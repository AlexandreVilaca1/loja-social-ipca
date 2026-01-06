
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



public interface CreateDonationItemMutation :
    com.google.firebase.dataconnect.generated.GeneratedMutation<
      SasConnectorConnector,
      CreateDonationItemMutation.Data,
      CreateDonationItemMutation.Variables
    >
{
  
    @kotlinx.serialization.Serializable
  public data class Variables(
  
    val donationId: DonationKey,
    val productId: ProductKey,
    val quantity: Int,
    val observations: com.google.firebase.dataconnect.OptionalVariable<String?>
  ) {
    
    
      
      @kotlin.DslMarker public annotation class BuilderDsl

      @BuilderDsl
      public interface Builder {
        public var donationId: DonationKey
        public var productId: ProductKey
        public var quantity: Int
        public var observations: String?
        
      }

      public companion object {
        @Suppress("NAME_SHADOWING")
        public fun build(
          donationId: DonationKey,productId: ProductKey,quantity: Int,
          block_: Builder.() -> Unit
        ): Variables {
          var donationId= donationId
            var productId= productId
            var quantity= quantity
            var observations: com.google.firebase.dataconnect.OptionalVariable<String?> =
                com.google.firebase.dataconnect.OptionalVariable.Undefined
            

          return object : Builder {
            override var donationId: DonationKey
              get() = throw UnsupportedOperationException("getting builder values is not supported")
              set(value_) { donationId = value_ }
              
            override var productId: ProductKey
              get() = throw UnsupportedOperationException("getting builder values is not supported")
              set(value_) { productId = value_ }
              
            override var quantity: Int
              get() = throw UnsupportedOperationException("getting builder values is not supported")
              set(value_) { quantity = value_ }
              
            override var observations: String?
              get() = throw UnsupportedOperationException("getting builder values is not supported")
              set(value_) { observations = com.google.firebase.dataconnect.OptionalVariable.Value(value_) }
              
            
          }.apply(block_)
          .let {
            Variables(
              donationId=donationId,productId=productId,quantity=quantity,observations=observations,
            )
          }
        }
      }
    
  }
  

  
    @kotlinx.serialization.Serializable
  public data class Data(
  
    val donationItem_insert: DonationItemKey
  ) {
    
    
  }
  

  public companion object {
    public val operationName: String = "CreateDonationItem"

    public val dataDeserializer: kotlinx.serialization.DeserializationStrategy<Data> =
      kotlinx.serialization.serializer()

    public val variablesSerializer: kotlinx.serialization.SerializationStrategy<Variables> =
      kotlinx.serialization.serializer()
  }
}

public fun CreateDonationItemMutation.ref(
  
    donationId: DonationKey,productId: ProductKey,quantity: Int,
  
    block_: CreateDonationItemMutation.Variables.Builder.() -> Unit = {}
  
): com.google.firebase.dataconnect.MutationRef<
    CreateDonationItemMutation.Data,
    CreateDonationItemMutation.Variables
  > =
  ref(
    
      CreateDonationItemMutation.Variables.build(
        donationId=donationId,productId=productId,quantity=quantity,
  
    block_
      )
    
  )

public suspend fun CreateDonationItemMutation.execute(
  
    donationId: DonationKey,productId: ProductKey,quantity: Int,
  
    block_: CreateDonationItemMutation.Variables.Builder.() -> Unit = {}
  
  ): com.google.firebase.dataconnect.MutationResult<
    CreateDonationItemMutation.Data,
    CreateDonationItemMutation.Variables
  > =
  ref(
    
      donationId=donationId,productId=productId,quantity=quantity,
  
    block_
    
  ).execute()


