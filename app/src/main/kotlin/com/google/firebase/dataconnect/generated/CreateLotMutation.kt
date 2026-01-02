
@file:kotlin.Suppress(
  "KotlinRedundantDiagnosticSuppress",
  "LocalVariableName",
  "MayBeConstant",
  "RedundantVisibilityModifier",
  "RemoveEmptyClassBody",
  "SpellCheckingInspection",
  "LocalVariableName",
  "unused",
)

package com.google.firebase.dataconnect.generated



public interface CreateLotMutation :
    com.google.firebase.dataconnect.generated.GeneratedMutation<
      SasConnectorConnector,
      CreateLotMutation.Data,
      CreateLotMutation.Variables
    >
{
  
    @kotlinx.serialization.Serializable
  public data class Variables(
  
    val initialQuantity: Int,
    val currentQuantity: Int,
    val entryDate: com.google.firebase.dataconnect.LocalDate,
    val lotCode: String,
    val expirationDate: com.google.firebase.dataconnect.LocalDate,
    val observations: com.google.firebase.dataconnect.OptionalVariable<String?>,
    val productId: com.google.firebase.dataconnect.OptionalVariable<ProductKey?>
  ) {
    
    
      
      @kotlin.DslMarker public annotation class BuilderDsl

      @BuilderDsl
      public interface Builder {
        public var initialQuantity: Int
        public var currentQuantity: Int
        public var entryDate: com.google.firebase.dataconnect.LocalDate
        public var lotCode: String
        public var expirationDate: com.google.firebase.dataconnect.LocalDate
        public var observations: String?
        public var productId: ProductKey?
        
      }

      public companion object {
        @Suppress("NAME_SHADOWING")
        public fun build(
          initialQuantity: Int,currentQuantity: Int,entryDate: com.google.firebase.dataconnect.LocalDate,lotCode: String,expirationDate: com.google.firebase.dataconnect.LocalDate,
          block_: Builder.() -> Unit
        ): Variables {
          var initialQuantity= initialQuantity
            var currentQuantity= currentQuantity
            var entryDate= entryDate
            var lotCode= lotCode
            var expirationDate= expirationDate
            var observations: com.google.firebase.dataconnect.OptionalVariable<String?> =
                com.google.firebase.dataconnect.OptionalVariable.Undefined
            var productId: com.google.firebase.dataconnect.OptionalVariable<ProductKey?> =
                com.google.firebase.dataconnect.OptionalVariable.Undefined
            

          return object : Builder {
            override var initialQuantity: Int
              get() = throw UnsupportedOperationException("getting builder values is not supported")
              set(value_) { initialQuantity = value_ }
              
            override var currentQuantity: Int
              get() = throw UnsupportedOperationException("getting builder values is not supported")
              set(value_) { currentQuantity = value_ }
              
            override var entryDate: com.google.firebase.dataconnect.LocalDate
              get() = throw UnsupportedOperationException("getting builder values is not supported")
              set(value_) { entryDate = value_ }
              
            override var lotCode: String
              get() = throw UnsupportedOperationException("getting builder values is not supported")
              set(value_) { lotCode = value_ }
              
            override var expirationDate: com.google.firebase.dataconnect.LocalDate
              get() = throw UnsupportedOperationException("getting builder values is not supported")
              set(value_) { expirationDate = value_ }
              
            override var observations: String?
              get() = throw UnsupportedOperationException("getting builder values is not supported")
              set(value_) { observations = com.google.firebase.dataconnect.OptionalVariable.Value(value_) }
              
            override var productId: ProductKey?
              get() = throw UnsupportedOperationException("getting builder values is not supported")
              set(value_) { productId = com.google.firebase.dataconnect.OptionalVariable.Value(value_) }
              
            
          }.apply(block_)
          .let {
            Variables(
              initialQuantity=initialQuantity,currentQuantity=currentQuantity,entryDate=entryDate,lotCode=lotCode,expirationDate=expirationDate,observations=observations,productId=productId,
            )
          }
        }
      }
    
  }
  

  
    @kotlinx.serialization.Serializable
  public data class Data(
  
    val lot_insert: LotKey
  ) {
    
    
  }
  

  public companion object {
    public val operationName: String = "CreateLot"

    public val dataDeserializer: kotlinx.serialization.DeserializationStrategy<Data> =
      kotlinx.serialization.serializer()

    public val variablesSerializer: kotlinx.serialization.SerializationStrategy<Variables> =
      kotlinx.serialization.serializer()
  }
}

public fun CreateLotMutation.ref(
  
    initialQuantity: Int,currentQuantity: Int,entryDate: com.google.firebase.dataconnect.LocalDate,lotCode: String,expirationDate: com.google.firebase.dataconnect.LocalDate,
  
    block_: CreateLotMutation.Variables.Builder.() -> Unit = {}
  
): com.google.firebase.dataconnect.MutationRef<
    CreateLotMutation.Data,
    CreateLotMutation.Variables
  > =
  ref(
    
      CreateLotMutation.Variables.build(
        initialQuantity=initialQuantity,currentQuantity=currentQuantity,entryDate=entryDate,lotCode=lotCode,expirationDate=expirationDate,
  
    block_
      )
    
  )

public suspend fun CreateLotMutation.execute(
  
    initialQuantity: Int,currentQuantity: Int,entryDate: com.google.firebase.dataconnect.LocalDate,lotCode: String,expirationDate: com.google.firebase.dataconnect.LocalDate,
  
    block_: CreateLotMutation.Variables.Builder.() -> Unit = {}
  
  ): com.google.firebase.dataconnect.MutationResult<
    CreateLotMutation.Data,
    CreateLotMutation.Variables
  > =
  ref(
    
      initialQuantity=initialQuantity,currentQuantity=currentQuantity,entryDate=entryDate,lotCode=lotCode,expirationDate=expirationDate,
  
    block_
    
  ).execute()


