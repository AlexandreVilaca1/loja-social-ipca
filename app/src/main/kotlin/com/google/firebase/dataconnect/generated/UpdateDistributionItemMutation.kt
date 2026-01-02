
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



public interface UpdateDistributionItemMutation :
    com.google.firebase.dataconnect.generated.GeneratedMutation<
      SasConnectorConnector,
      UpdateDistributionItemMutation.Data,
      UpdateDistributionItemMutation.Variables
    >
{
  
    @kotlinx.serialization.Serializable
  public data class Variables(
  
    val distributionId: @kotlinx.serialization.Serializable(with = com.google.firebase.dataconnect.serializers.UUIDSerializer::class) java.util.UUID,
    val lotId: @kotlinx.serialization.Serializable(with = com.google.firebase.dataconnect.serializers.UUIDSerializer::class) java.util.UUID,
    val quantity: com.google.firebase.dataconnect.OptionalVariable<Int?>,
    val observations: com.google.firebase.dataconnect.OptionalVariable<String?>
  ) {
    
    
      
      @kotlin.DslMarker public annotation class BuilderDsl

      @BuilderDsl
      public interface Builder {
        public var distributionId: java.util.UUID
        public var lotId: java.util.UUID
        public var quantity: Int?
        public var observations: String?
        
      }

      public companion object {
        @Suppress("NAME_SHADOWING")
        public fun build(
          distributionId: java.util.UUID,lotId: java.util.UUID,
          block_: Builder.() -> Unit
        ): Variables {
          var distributionId= distributionId
            var lotId= lotId
            var quantity: com.google.firebase.dataconnect.OptionalVariable<Int?> =
                com.google.firebase.dataconnect.OptionalVariable.Undefined
            var observations: com.google.firebase.dataconnect.OptionalVariable<String?> =
                com.google.firebase.dataconnect.OptionalVariable.Undefined
            

          return object : Builder {
            override var distributionId: java.util.UUID
              get() = throw UnsupportedOperationException("getting builder values is not supported")
              set(value_) { distributionId = value_ }
              
            override var lotId: java.util.UUID
              get() = throw UnsupportedOperationException("getting builder values is not supported")
              set(value_) { lotId = value_ }
              
            override var quantity: Int?
              get() = throw UnsupportedOperationException("getting builder values is not supported")
              set(value_) { quantity = com.google.firebase.dataconnect.OptionalVariable.Value(value_) }
              
            override var observations: String?
              get() = throw UnsupportedOperationException("getting builder values is not supported")
              set(value_) { observations = com.google.firebase.dataconnect.OptionalVariable.Value(value_) }
              
            
          }.apply(block_)
          .let {
            Variables(
              distributionId=distributionId,lotId=lotId,quantity=quantity,observations=observations,
            )
          }
        }
      }
    
  }
  

  
    @kotlinx.serialization.Serializable
  public data class Data(
  
    val distributionItem_update: DistributionItemKey?
  ) {
    
    
  }
  

  public companion object {
    public val operationName: String = "UpdateDistributionItem"

    public val dataDeserializer: kotlinx.serialization.DeserializationStrategy<Data> =
      kotlinx.serialization.serializer()

    public val variablesSerializer: kotlinx.serialization.SerializationStrategy<Variables> =
      kotlinx.serialization.serializer()
  }
}

public fun UpdateDistributionItemMutation.ref(
  
    distributionId: java.util.UUID,lotId: java.util.UUID,
  
    block_: UpdateDistributionItemMutation.Variables.Builder.() -> Unit = {}
  
): com.google.firebase.dataconnect.MutationRef<
    UpdateDistributionItemMutation.Data,
    UpdateDistributionItemMutation.Variables
  > =
  ref(
    
      UpdateDistributionItemMutation.Variables.build(
        distributionId=distributionId,lotId=lotId,
  
    block_
      )
    
  )

public suspend fun UpdateDistributionItemMutation.execute(
  
    distributionId: java.util.UUID,lotId: java.util.UUID,
  
    block_: UpdateDistributionItemMutation.Variables.Builder.() -> Unit = {}
  
  ): com.google.firebase.dataconnect.MutationResult<
    UpdateDistributionItemMutation.Data,
    UpdateDistributionItemMutation.Variables
  > =
  ref(
    
      distributionId=distributionId,lotId=lotId,
  
    block_
    
  ).execute()


