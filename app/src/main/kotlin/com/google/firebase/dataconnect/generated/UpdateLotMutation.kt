
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



public interface UpdateLotMutation :
    com.google.firebase.dataconnect.generated.GeneratedMutation<
      SasConnectorConnector,
      UpdateLotMutation.Data,
      UpdateLotMutation.Variables
    >
{
  
    @kotlinx.serialization.Serializable
  public data class Variables(
  
    val id: @kotlinx.serialization.Serializable(with = com.google.firebase.dataconnect.serializers.UUIDSerializer::class) java.util.UUID,
    val initialQuantity: com.google.firebase.dataconnect.OptionalVariable<Int?>,
    val currentQuantity: com.google.firebase.dataconnect.OptionalVariable<Int?>,
    val entryDate: com.google.firebase.dataconnect.OptionalVariable<com.google.firebase.dataconnect.LocalDate?>,
    val lotCode: com.google.firebase.dataconnect.OptionalVariable<String?>,
    val expirationDate: com.google.firebase.dataconnect.OptionalVariable<com.google.firebase.dataconnect.LocalDate?>,
    val observations: com.google.firebase.dataconnect.OptionalVariable<String?>,
    val productId: com.google.firebase.dataconnect.OptionalVariable<ProductKey?>
  ) {
    
    
      
      @kotlin.DslMarker public annotation class BuilderDsl

      @BuilderDsl
      public interface Builder {
        public var id: java.util.UUID
        public var initialQuantity: Int?
        public var currentQuantity: Int?
        public var entryDate: com.google.firebase.dataconnect.LocalDate?
        public var lotCode: String?
        public var expirationDate: com.google.firebase.dataconnect.LocalDate?
        public var observations: String?
        public var productId: ProductKey?
        
      }

      public companion object {
        @Suppress("NAME_SHADOWING")
        public fun build(
          id: java.util.UUID,
          block_: Builder.() -> Unit
        ): Variables {
          var id= id
            var initialQuantity: com.google.firebase.dataconnect.OptionalVariable<Int?> =
                com.google.firebase.dataconnect.OptionalVariable.Undefined
            var currentQuantity: com.google.firebase.dataconnect.OptionalVariable<Int?> =
                com.google.firebase.dataconnect.OptionalVariable.Undefined
            var entryDate: com.google.firebase.dataconnect.OptionalVariable<com.google.firebase.dataconnect.LocalDate?> =
                com.google.firebase.dataconnect.OptionalVariable.Undefined
            var lotCode: com.google.firebase.dataconnect.OptionalVariable<String?> =
                com.google.firebase.dataconnect.OptionalVariable.Undefined
            var expirationDate: com.google.firebase.dataconnect.OptionalVariable<com.google.firebase.dataconnect.LocalDate?> =
                com.google.firebase.dataconnect.OptionalVariable.Undefined
            var observations: com.google.firebase.dataconnect.OptionalVariable<String?> =
                com.google.firebase.dataconnect.OptionalVariable.Undefined
            var productId: com.google.firebase.dataconnect.OptionalVariable<ProductKey?> =
                com.google.firebase.dataconnect.OptionalVariable.Undefined
            

          return object : Builder {
            override var id: java.util.UUID
              get() = throw UnsupportedOperationException("getting builder values is not supported")
              set(value_) { id = value_ }
              
            override var initialQuantity: Int?
              get() = throw UnsupportedOperationException("getting builder values is not supported")
              set(value_) { initialQuantity = com.google.firebase.dataconnect.OptionalVariable.Value(value_) }
              
            override var currentQuantity: Int?
              get() = throw UnsupportedOperationException("getting builder values is not supported")
              set(value_) { currentQuantity = com.google.firebase.dataconnect.OptionalVariable.Value(value_) }
              
            override var entryDate: com.google.firebase.dataconnect.LocalDate?
              get() = throw UnsupportedOperationException("getting builder values is not supported")
              set(value_) { entryDate = com.google.firebase.dataconnect.OptionalVariable.Value(value_) }
              
            override var lotCode: String?
              get() = throw UnsupportedOperationException("getting builder values is not supported")
              set(value_) { lotCode = com.google.firebase.dataconnect.OptionalVariable.Value(value_) }
              
            override var expirationDate: com.google.firebase.dataconnect.LocalDate?
              get() = throw UnsupportedOperationException("getting builder values is not supported")
              set(value_) { expirationDate = com.google.firebase.dataconnect.OptionalVariable.Value(value_) }
              
            override var observations: String?
              get() = throw UnsupportedOperationException("getting builder values is not supported")
              set(value_) { observations = com.google.firebase.dataconnect.OptionalVariable.Value(value_) }
              
            override var productId: ProductKey?
              get() = throw UnsupportedOperationException("getting builder values is not supported")
              set(value_) { productId = com.google.firebase.dataconnect.OptionalVariable.Value(value_) }
              
            
          }.apply(block_)
          .let {
            Variables(
              id=id,initialQuantity=initialQuantity,currentQuantity=currentQuantity,entryDate=entryDate,lotCode=lotCode,expirationDate=expirationDate,observations=observations,productId=productId,
            )
          }
        }
      }
    
  }
  

  
    @kotlinx.serialization.Serializable
  public data class Data(
  
    val lot_update: LotKey?
  ) {
    
    
  }
  

  public companion object {
    public val operationName: String = "UpdateLot"

    public val dataDeserializer: kotlinx.serialization.DeserializationStrategy<Data> =
      kotlinx.serialization.serializer()

    public val variablesSerializer: kotlinx.serialization.SerializationStrategy<Variables> =
      kotlinx.serialization.serializer()
  }
}

public fun UpdateLotMutation.ref(
  
    id: java.util.UUID,
  
    block_: UpdateLotMutation.Variables.Builder.() -> Unit = {}
  
): com.google.firebase.dataconnect.MutationRef<
    UpdateLotMutation.Data,
    UpdateLotMutation.Variables
  > =
  ref(
    
      UpdateLotMutation.Variables.build(
        id=id,
  
    block_
      )
    
  )

public suspend fun UpdateLotMutation.execute(
  
    id: java.util.UUID,
  
    block_: UpdateLotMutation.Variables.Builder.() -> Unit = {}
  
  ): com.google.firebase.dataconnect.MutationResult<
    UpdateLotMutation.Data,
    UpdateLotMutation.Variables
  > =
  ref(
    
      id=id,
  
    block_
    
  ).execute()


