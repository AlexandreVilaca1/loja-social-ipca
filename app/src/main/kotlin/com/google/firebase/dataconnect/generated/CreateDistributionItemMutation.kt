
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



public interface CreateDistributionItemMutation :
    com.google.firebase.dataconnect.generated.GeneratedMutation<
      SasConnectorConnector,
      CreateDistributionItemMutation.Data,
      CreateDistributionItemMutation.Variables
    >
{
  
    @kotlinx.serialization.Serializable
  public data class Variables(
  
    val distributionId: DistributionKey,
    val lotId: LotKey,
    val quantity: Int,
    val observations: com.google.firebase.dataconnect.OptionalVariable<String?>
  ) {
    
    
      
      @kotlin.DslMarker public annotation class BuilderDsl

      @BuilderDsl
      public interface Builder {
        public var distributionId: DistributionKey
        public var lotId: LotKey
        public var quantity: Int
        public var observations: String?
        
      }

      public companion object {
        @Suppress("NAME_SHADOWING")
        public fun build(
          distributionId: DistributionKey,lotId: LotKey,quantity: Int,
          block_: Builder.() -> Unit
        ): Variables {
          var distributionId= distributionId
            var lotId= lotId
            var quantity= quantity
            var observations: com.google.firebase.dataconnect.OptionalVariable<String?> =
                com.google.firebase.dataconnect.OptionalVariable.Undefined
            

          return object : Builder {
            override var distributionId: DistributionKey
              get() = throw UnsupportedOperationException("getting builder values is not supported")
              set(value_) { distributionId = value_ }
              
            override var lotId: LotKey
              get() = throw UnsupportedOperationException("getting builder values is not supported")
              set(value_) { lotId = value_ }
              
            override var quantity: Int
              get() = throw UnsupportedOperationException("getting builder values is not supported")
              set(value_) { quantity = value_ }
              
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
  
    val distributionItem_insert: DistributionItemKey
  ) {
    
    
  }
  

  public companion object {
    public val operationName: String = "CreateDistributionItem"

    public val dataDeserializer: kotlinx.serialization.DeserializationStrategy<Data> =
      kotlinx.serialization.serializer()

    public val variablesSerializer: kotlinx.serialization.SerializationStrategy<Variables> =
      kotlinx.serialization.serializer()
  }
}

public fun CreateDistributionItemMutation.ref(
  
    distributionId: DistributionKey,lotId: LotKey,quantity: Int,
  
    block_: CreateDistributionItemMutation.Variables.Builder.() -> Unit = {}
  
): com.google.firebase.dataconnect.MutationRef<
    CreateDistributionItemMutation.Data,
    CreateDistributionItemMutation.Variables
  > =
  ref(
    
      CreateDistributionItemMutation.Variables.build(
        distributionId=distributionId,lotId=lotId,quantity=quantity,
  
    block_
      )
    
  )

public suspend fun CreateDistributionItemMutation.execute(
  
    distributionId: DistributionKey,lotId: LotKey,quantity: Int,
  
    block_: CreateDistributionItemMutation.Variables.Builder.() -> Unit = {}
  
  ): com.google.firebase.dataconnect.MutationResult<
    CreateDistributionItemMutation.Data,
    CreateDistributionItemMutation.Variables
  > =
  ref(
    
      distributionId=distributionId,lotId=lotId,quantity=quantity,
  
    block_
    
  ).execute()


