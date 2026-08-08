// ==============================================
// DMMF Chapter 9 练习骨架 单fsx脚本
// 执行顺序：从上到下依次选中运行
// ==============================================
open System

// ################################################
// 一、值对象定义（第7章范式，按需扩充）
// ################################################

module Domain =
    // ======================================
    // OrderId
    // ======================================
    type OrderId = private OrderId of string

    module OrderId =
        /// string -> OrderId
        let create (str: string) : OrderId =
            if String.IsNullOrEmpty(str) then
                failwith "OrderId must not be null or empty"
            elif str.Length > 50 then
                failwith "OrderId must not be more than 50 chars"
            else
                OrderId str

        /// OrderId -> string
        let value (OrderId str) = str

    // ======================================
    // String50 通用短文本：姓名、街道、城市等
    // ======================================
    type String50 = private String50 of string

    module String50 =
        /// string -> String50
        let create (str: string) : String50 =
            if String.IsNullOrEmpty(str) then
                failwith "Must not be empty"
            elif str.Length > 50 then
                failwith "Cannot be longer than 50 characters"
            else
                String50 str

        // 补上缺失的createOption
        let createOption (s: string) =
            if System.String.IsNullOrEmpty(s) then
                None
            else
                Some(create s)

        /// String50 -> string
        let value (String50 str) = str

    // ======================================
    // EmailAddress
    // ======================================
    type EmailAddress = private EmailAddress of string

    module EmailAddress =
        /// string -> EmailAddress
        let create (str: string) : EmailAddress =
            if String.IsNullOrEmpty(str) then
                failwith "Email must not be empty"
            elif str.Length > 50 then
                failwith "Email cannot be longer than 50 characters"
            elif not (str.Contains("@")) then
                failwith "Email must contain @"
            else
                EmailAddress str

        /// EmailAddress -> string
        let value (EmailAddress str) = str

    // ======================================
    // UnitQuantity (Widget 数量，整数)
    // ======================================
    type UnitQuantity = private UnitQuantity of int

    module UnitQuantity =
        /// int -> UnitQuantity
        let create (qty: int) : UnitQuantity =
            if qty < 1 then
                failwith "Unit quantity must be greater than zero"
            elif qty > 1000 then
                failwith "Unit quantity cannot be more than 1000"
            else
                UnitQuantity qty

        /// UnitQuantity -> int
        let value (UnitQuantity v) = v

    // ======================================
    // KilogramQuantity (Gizmo 重量，decimal)
    // ======================================
    type KilogramQuantity = private KilogramQuantity of decimal

    module KilogramQuantity =
        /// decimal -> KilogramQuantity
        let create (qty: decimal) : KilogramQuantity =
            if qty <= 0m then
                failwith "Kilogram quantity must be greater than zero"
            elif qty > 100m then
                failwith "Kilogram quantity cannot be more than 100"
            else
                KilogramQuantity qty

        /// KilogramQuantity -> decimal
        let value (KilogramQuantity v) = v

    // ======================================
    // OrderQuantity：二选一，按产品类型区分数量
    // ======================================
    type OrderQuantity =
        | Unit of UnitQuantity
        | Kilogram of KilogramQuantity

    // ======================================
    // ProductCode 可区分联合
    // ======================================
    type ProductCode =
        | Widget of String50
        | Gizmo of String50

    type ZipCode = private ZipCode of string

    module ZipCode =
        let create (s: string) : ZipCode =
            // 邮编校验逻辑，失败failwith
            if String.IsNullOrEmpty(s) then
                failwith "zip must not empty"
            elif s.Length > 10 then
                failwith "zip too long"
            else
                ZipCode s

        let value (ZipCode s) = s

    type OrderLineId = private OrderLineId of string

    module OrderLineId =
        let create (s: string) =
            if System.String.IsNullOrEmpty s then
                failwith "OrderLineId cannot be empty"
            else
                OrderLineId s

        let value (OrderLineId v) = v

    // ################################################
    // 二、领域复合模型（可信内部类型，积类型 Record）
    // ################################################
    type PersonalName =
        { FirstName: String50
          LastName: String50 }

    type Address =
        { AddressLine1: String50
          AddressLine2: String50 option
          AddressLine3: String50 option
          AddressLine4: String50 option
          City: String50
          ZipCode: ZipCode }

    type CustomerInfo =
        { Name: PersonalName
          EmailAddress: EmailAddress }

    type ValidatedOrderLine =
        { OrderLineId: OrderLineId
          ProductCode: ProductCode
          Quantity: OrderQuantity }

    type ValidatedOrder =
        { OrderId: OrderId
          CustomerInfo: CustomerInfo
          ShippingAddress: Address
          Lines: ValidatedOrderLine list }

    // ################################################
    // 三、外部原始输入DTO（不可信原始数据）
    // ################################################
    type UnvalidatedCustomerInfo =
        { FirstName: string
          LastName: string
          EmailAddress: string }

    type UnvalidatedAddress =
        { Street: string
          City: string
          State: string
          ZipCode: string }

    type UnvalidatedOrderLine =
        { OrderLineId: string
          ProductCode: string
          Quantity: decimal }

    type UnvalidatedOrder =
        { OrderId: string
          CustomerInfo: UnvalidatedCustomerInfo
          ShippingAddress: UnvalidatedAddress
          Lines: UnvalidatedOrderLine list }

    // ################################################
    // 四、外部依赖函数类型别名（DMMF依赖注入范式）
    // ################################################
    type CheckProductCodeExists = string -> ProductCode

    // 外部服务返回的包裹类型
    type CheckedAddressData =
        { AddressLine1: string
          AddressLine2: string
          AddressLine3: string
          AddressLine4: string
          City: string
          ZipCode: string }

    type CheckedAddress = CheckedAddress of CheckedAddressData
    type CheckAddressExists = UnvalidatedAddress -> CheckedAddress

    // ################################################
    // 五、转换函数 toXXX 【待你逐步实现】
    // ################################################

    let toCustomerInfo (customer: UnvalidatedCustomerInfo) : CustomerInfo =
        // create the various CustomerInfo properties
        // and throw exceptions if invalid
        let firstName = customer.FirstName |> String50.create
        let lastName = customer.LastName |> String50.create
        let emailAddress = customer.EmailAddress |> EmailAddress.create

        // create a PersonalName
        let name: PersonalName =
            { FirstName = firstName
              LastName = lastName }

        // create a CustomerInfo
        let customerInfo: CustomerInfo =
            { Name = name
              EmailAddress = emailAddress }
        // ... and return it
        customerInfo

    let toAddress (checkAddressExists: CheckAddressExists) (unvalidatedAddress: UnvalidatedAddress) : Address =
        // call the remote service
        let checkedAddress = checkAddressExists unvalidatedAddress
        // extract the inner value using pattern matching
        let (CheckedAddress checkedAddress) = checkedAddress

        let addressLine1 = checkedAddress.AddressLine1 |> String50.create
        let addressLine2 = checkedAddress.AddressLine2 |> String50.createOption
        let addressLine3 = checkedAddress.AddressLine3 |> String50.createOption
        let addressLine4 = checkedAddress.AddressLine4 |> String50.createOption
        let city = checkedAddress.City |> String50.create
        let zipCode = checkedAddress.ZipCode |> ZipCode.create
        // create the address
        let address: Address =
            { AddressLine1 = addressLine1
              AddressLine2 = addressLine2
              AddressLine3 = addressLine3
              AddressLine4 = addressLine4
              City = city
              ZipCode = zipCode }
        // return the address
        address

    let toProductCode (checkProductCodeExists: CheckProductCodeExists) (productCodeStr: string) : ProductCode =
        checkProductCodeExists productCodeStr

    let toOrderQuantity (productCode: ProductCode) (quantity: decimal) : OrderQuantity =
        match productCode with
        | Widget _ -> quantity |> int |> UnitQuantity.create |> OrderQuantity.Unit

        | Gizmo _ -> quantity |> KilogramQuantity.create |> OrderQuantity.Kilogram

    let toValidatedOrderLine
        (checkProductCodeExists: CheckProductCodeExists)
        (unvalidatedOrderLine: UnvalidatedOrderLine)
        : ValidatedOrderLine =

        let orderLineId = unvalidatedOrderLine.OrderLineId |> OrderLineId.create

        let productCode =
            unvalidatedOrderLine.ProductCode |> toProductCode checkProductCodeExists

        let quantity = unvalidatedOrderLine.Quantity |> toOrderQuantity productCode

        let validatedOrderLine: ValidatedOrderLine =
            { OrderLineId = orderLineId
              ProductCode = productCode
              Quantity = quantity }

        validatedOrderLine

    // 顶层主函数 validateOrder
    type ValidateOrder = CheckProductCodeExists -> CheckAddressExists -> UnvalidatedOrder -> ValidatedOrder

    let validateOrder: ValidateOrder =
        fun
            (checkProductCodeExists: CheckProductCodeExists)
            (checkAddressExists: CheckAddressExists)
            (unvalidatedOrder: UnvalidatedOrder) ->

            let orderId = unvalidatedOrder.OrderId |> OrderId.create

            let customerInfo = unvalidatedOrder.CustomerInfo |> toCustomerInfo

            let shippingAddress =
                unvalidatedOrder.ShippingAddress |> toAddress checkAddressExists

            // 将每一行未校验订单行，转换为 ValidatedOrderLine
            let validatedLines =
                unvalidatedOrder.Lines |> List.map (toValidatedOrderLine checkProductCodeExists)

            // 组装完整 ValidatedOrder 记录返回
            { OrderId = orderId
              CustomerInfo = customerInfo
              ShippingAddress = shippingAddress
              Lines = validatedLines }
    // ==============================================================================================================
    // Implementing the Rest of the Steps
    // ==============================================================================================================
    // type PriceOrder = GetProductPrice -> ValidatedOrder -> PricedOrder
    type Price = private Price of decimal

    module Price =
        let create (value: decimal) : Price =
            if value <= 0m then
                failwith "Price must be positive"
            else
                Price value

        /// 取出内部decimal原始值，解包
        let value (Price innerDecimal) : decimal = innerDecimal
    
        // ✅和create保持同样缩进，属于Price模块内函数 → Price.multiply
        let multiply (qty: OrderQuantity) (price: Price) : Price =
            let (Price p) = price

            let qtyDecimal =
                match qty with
                | Unit(UnitQuantity u) -> decimal u
                | Kilogram(KilogramQuantity kg) -> kg

            create (p * qtyDecimal)

    /// 定价后的订单行
    type PricedOrderLine =
        { OrderLineId: OrderLineId
          ProductCode: ProductCode
          Quantity: OrderQuantity
          LinePrice: Price }

    type BillingAmount = private BillingAmount of Price

    module BillingAmount =
        let create (price: Price) : BillingAmount = BillingAmount price

        let value (BillingAmount p) = p

        /// 把一组Price累加，输出BillingAmount
        let sumPrices (prices: Price list) : BillingAmount =
            let totalDecimal = prices |> List.sumBy (fun (Price p) -> p)
            create (Price.create totalDecimal)

    /// 定价完成的订单
    type PricedOrder =
        { OrderId: OrderId
          CustomerInfo: CustomerInfo
          ShippingAddress: Address
          BillingAddress: Address
          Lines: PricedOrderLine list
          AmountToBill: BillingAmount }

    type GetProductPrice = ProductCode -> Price
    type PriceOrder = GetProductPrice -> ValidatedOrder -> PricedOrder
    type ToPricedOrderLine = GetProductPrice -> ValidatedOrderLine -> PricedOrderLine

    let toPricedOrderLine: ToPricedOrderLine =
        fun (getProductPrice: GetProductPrice) (validatedLine: ValidatedOrderLine) ->
            let price = getProductPrice validatedLine.ProductCode
            let linePrice = Price.multiply validatedLine.Quantity price

            { OrderLineId = validatedLine.OrderLineId
              ProductCode = validatedLine.ProductCode
              Quantity = validatedLine.Quantity
              LinePrice = linePrice }

    let priceOrder: PriceOrder =
        fun (getProductPrice: GetProductPrice) (validatedOrder: ValidatedOrder) ->
            let lines = validatedOrder.Lines |> List.map (toPricedOrderLine getProductPrice)

            let amountToBill =
                lines |> List.map (fun line -> line.LinePrice) |> BillingAmount.sumPrices

            let pricedOrder: PricedOrder =
                { OrderId = validatedOrder.OrderId
                  CustomerInfo = validatedOrder.CustomerInfo
                  ShippingAddress = validatedOrder.ShippingAddress
                  BillingAddress = validatedOrder.ShippingAddress
                  Lines = lines
                  AmountToBill = amountToBill }

            pricedOrder

    // Implementing the Acknowledgement Step
    /// 包装HTML字符串，领域专用类型，避免普通string混用
    type HtmlString = HtmlString of string

    /// 依赖：根据定价订单生成确认邮件HTML信件
    type CreateOrderAcknowledgmentLetter = PricedOrder -> HtmlString

    /// 订单确认邮件内容
    type OrderAcknowledgment =
        { EmailAddress: EmailAddress
          Letter: HtmlString }

    /// 发送结果：发送成功 / 发送失败
    type SendResult =
        | Sent
        | NotSent

    /// 依赖：发送订单确认邮件
    type SendOrderAcknowledgment = OrderAcknowledgment -> SendResult

    /// 领域事件：订单确认已发送
    type OrderAcknowledgmentSent =
        { OrderId: OrderId
          EmailAddress: EmailAddress }

    /// 工作流函数类型：acknowledgeOrder
    /// 输入两个外部依赖 + PricedOrder；成功返回事件Some，失败返回None
    type AcknowledgeOrder =
        CreateOrderAcknowledgmentLetter -> SendOrderAcknowledgment -> PricedOrder -> OrderAcknowledgmentSent option

    let acknowledgeOrder: AcknowledgeOrder =
        fun
            (createAcknowledgmentLetter: CreateOrderAcknowledgmentLetter)
            (sendAcknowledgment: SendOrderAcknowledgment)
            (pricedOrder: PricedOrder) ->
            let letter: HtmlString = createAcknowledgmentLetter pricedOrder

            let acknowledgment: OrderAcknowledgment =
                { EmailAddress = pricedOrder.CustomerInfo.EmailAddress
                  Letter = letter }

            match sendAcknowledgment acknowledgment with
            | Sent ->
                let orderAcknowledgedEvent: OrderAcknowledgmentSent =
                    { OrderId = pricedOrder.OrderId
                      EmailAddress = pricedOrder.CustomerInfo.EmailAddress }

                Some orderAcknowledgedEvent: OrderAcknowledgmentSent option

            | NotSent -> None: OrderAcknowledgmentSent option

    // Creating the Events
    /// Event to send to shipping context
    type OrderPlaced = PricedOrder

    /// Event to send to billing context
    /// Will only be created if the AmountToBill is not zero
    type BillableOrderPlaced =
        { OrderId: OrderId
          BillingAddress: Address
          AmountToBill: BillingAmount }

    type PlaceOrderEvent =
        | OrderPlaced of OrderPlaced
        | BillableOrderPlaced of BillableOrderPlaced
        | AcknowledgmentSent of OrderAcknowledgmentSent

    type CreateEvents =
        PricedOrder // input
            -> OrderAcknowledgmentSent option // input (event from previous step)
            -> PlaceOrderEvent list // output

    // PricedOrder -> BillableOrderPlaced option
    let createBillingEvent (placedOrder:PricedOrder) : BillableOrderPlaced option =
        let billingAmount =
            placedOrder.AmountToBill
            |> BillingAmount.value   //得到 Price
            |> Price.value           //再解包拿到 decimal

        if billingAmount > 0M then
            let order = {
                OrderId = placedOrder.OrderId
                BillingAddress = placedOrder.BillingAddress
                AmountToBill = placedOrder.AmountToBill
            }
            Some order
        else
            None

    /// convert an Option into a List
    let listOfOption (opt: 'T option) : 'T list =
        match opt with
        | Some x -> [ x ]
        | None -> []

    let createEvents: CreateEvents =
        fun (pricedOrder: PricedOrder) (acknowledgmentEventOpt: OrderAcknowledgmentSent option) ->
            let events1: PlaceOrderEvent list =
                pricedOrder |> PlaceOrderEvent.OrderPlaced |> List.singleton

            let events2: PlaceOrderEvent list =
                acknowledgmentEventOpt
                |> Option.map PlaceOrderEvent.AcknowledgmentSent
                |> listOfOption

            let events3: PlaceOrderEvent list =
                pricedOrder
                |> createBillingEvent
                |> Option.map PlaceOrderEvent.BillableOrderPlaced
                |> listOfOption

            // return all the events
            [ yield! events1; yield! events2; yield! events3 ]

// Composing the Pipeline Steps Together
open Domain
/// 工作流：接收未校验订单，返回领域事件列表
type PlaceOrderWorkflow = UnvalidatedOrder -> PlaceOrderEvent list

// 所有外部依赖作为外层参数
let placeOrder
    (checkProductCodeExists:CheckProductCodeExists)
    (checkAddressExists:CheckAddressExists)
    (getProductPrice:GetProductPrice)
    (createAcknowledgmentLetter:CreateOrderAcknowledgmentLetter)
    (sendAcknowledgment:SendOrderAcknowledgment)
    : PlaceOrderWorkflow =

    fun unvalidatedOrder ->
        let validatedOrder =
            unvalidatedOrder
            |> validateOrder checkProductCodeExists checkAddressExists
        let pricedOrder =
            validatedOrder
            |> priceOrder getProductPrice
        let acknowledgementOption =
            pricedOrder
            |> acknowledgeOrder createAcknowledgmentLetter sendAcknowledgment
        let events =
            createEvents pricedOrder acknowledgementOption
        events



(*
// ==============================================================================================================
// 测试代码，放到脚本最下方，Domain模块外部
// ==============================================================================================================
open Domain

// ------------------------------
// Mock外部服务实现，对齐脚本里的类型定义
// ------------------------------
/// Mock：产品编码查询服务
let mockCheckProductCodeExists : CheckProductCodeExists =
    fun productCodeStr ->
        match productCodeStr with
        | s when s.StartsWith("W") -> Widget (String50.create s)
        | s when s.StartsWith("G") -> Gizmo (String50.create s)
        | other -> failwithf $"产品编码 %s{other} 不存在"

/// Mock：地址校验外部服务
let mockCheckAddressExists : CheckAddressExists =
    fun (unvalidatedAddr:UnvalidatedAddress) ->
        // 模拟外部服务处理，返回CheckedAddress包装类型
        let data = {
            AddressLine1 = unvalidatedAddr.Street
            AddressLine2 = ""
            AddressLine3 = ""
            AddressLine4 = ""
            City = unvalidatedAddr.City
            ZipCode = unvalidatedAddr.ZipCode
        }
        CheckedAddress data

// ------------------------------
// 构造完整的未校验订单测试数据 UnvalidatedOrder
// ------------------------------
let testUnvalidatedOrder : UnvalidatedOrder =
    {
        OrderId = "ORD‑0001"
        CustomerInfo =
            {
                FirstName = "Alex"
                LastName = "Adams"
                EmailAddress = "alex.adams@example.com"
            }
        ShippingAddress =
            {
                Street = "Renmin Road No.88"
                City = "Nanjing"
                State = "Jiangsu"
                ZipCode = "210001"
            }
        Lines =
            [
                // Widget产品，按件
                {
                    OrderLineId = "LINE‑01"
                    ProductCode = "W‑1001"
                    Quantity = 3m
                }
                // Gizmo产品，按公斤
                {
                    OrderLineId = "LINE‑02"
                    ProductCode = "G‑2002"
                    Quantity = 7.2m
                }
            ]
    }

// ------------------------------
// 执行 validateOrder 完整转换流程
// ------------------------------
printfn "==========开始执行validateOrder=========="

let testValidatedOrder : ValidatedOrder =
    validateOrder
        mockCheckProductCodeExists
        mockCheckAddressExists
        testUnvalidatedOrder

// 打印输出整个领域对象
printfn $"验证后订单结果:\n%A{testValidatedOrder}"
printfn "==========执行完成，无异常代表链路跑通=========="


// ------------------------------
// 可选：破坏性测试，打开下面注释，触发业务异常
// ------------------------------
(*
let badTestOrder = { testUnvalidatedOrder with Lines = [ { OrderLineId="L99"; ProductCode="X‑BAD"; Quantity=1m } ] }
let badResult = validateOrder mockCheckProductCodeExists mockCheckAddressExists badTestOrder
*)
*)
