
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



public interface UpdateDonationItemMutation :
    com.google.firebase.dataconnect.generated.GeneratedMutation<
      SasConnectorConnector,
      UpdateDonationItemMutation.Data,
      UpdateDonationItemMutation.Variables
    >
{
  
    @kotlinx.serialization.Serializable
  public data class Variables(
  
    val donationId: @kotlinx.serialization.Serializable(with = com.google.firebase.dataconnect.serializers.UUIDSerializer::class) java.util.UUID,
    val lotId: @kotlinx.serialization.Serializable(with = com.google.firebase.dataconnect.serializers.UUIDSerializer::class) java.util.UUID,
    val quantity: com.google.firebase.dataconnect.OptionalVariable<Int?>,
    val stateId: com.google.firebase.dataconnect.OptionalVariable<StatusTypeKey?>,
    val observations: com.google.firebase.dataconnect.OptionalVariable<String?>
  ) {
    
    
      
      @kotlin.DslMarker public annotation class BuilderDsl

      @BuilderDsl
      public interface Builder {
        public var donationId: java.util.UUID
        public var lotId: java.util.UUID
        public var quantity: Int?
        public var stateId: StatusTypeKey?
        public var observations: String?
        
      }

      public companion object {
        @Suppress("NAME_SHADOWING")
        public fun build(
          donationId: java.util.UUID,lotId: java.util.UUID,
          block_: Builder.() -> Unit
        ): Variables {
          var donationId= donationId
            var lotId= lotId
            var quantity: com.google.firebase.dataconnect.OptionalVariable<Int?> =
                com.google.firebase.dataconnect.OptionalVariable.Undefined
            var stateId: com.google.firebase.dataconnect.OptionalVariable<StatusTypeKey?> =
                com.google.firebase.dataconnect.OptionalVariable.Undefined
            var observations: com.google.firebase.dataconnect.OptionalVariable<String?> =
                com.google.firebase.dataconnect.OptionalVariable.Undefined
            

          return object : Builder {
            override var donationId: java.util.UUID
              get() = throw UnsupportedOperationException("getting builder values is not supported")
              set(value_) { donationId = value_ }
              
            override var lotId: java.util.UUID
              get() = throw UnsupportedOperationException("getting builder values is not supported")
              set(value_) { lotId = value_ }
              
            override var quantity: Int?
              get() = throw UnsupportedOperationException("getting builder values is not supported")
              set(value_) { quantity = com.google.firebase.dataconnect.OptionalVariable.Value(value_) }
              
            override var stateId: StatusTypeKey?
              get() = throw UnsupportedOperationException("getting builder values is not supported")
              set(value_) { stateId = com.google.firebase.dataconnect.OptionalVariable.Value(value_) }
              
            override var observations: String?
              get() = throw UnsupportedOperationException("getting builder values is not supported")
              set(value_) { observations = com.google.firebase.dataconnect.OptionalVariable.Value(value_) }
              
            
          }.apply(block_)
          .let {
            Variables(
              donationId=donationId,lotId=lotId,quantity=quantity,stateId=stateId,observations=observations,
            )
          }
        }
      }
    
  }
  

  
    @kotlinx.serialization.Serializable
  public data class Data(
  
    val donationItem_update: DonationItemKey?
  ) {
    
    
  }
  

  public companion object {
    public val operationName: String = "UpdateDonationItem"

    public val dataDeserializer: kotlinx.serialization.DeserializationStrategy<Data> =
      kotlinx.serialization.serializer()

    public val variablesSerializer: kotlinx.serialization.SerializationStrategy<Variables> =
      kotlinx.serialization.serializer()
  }
}

public fun UpdateDonationItemMutation.ref(
  
    donationId: java.util.UUID,lotId: java.util.UUID,
  
    block_: UpdateDonationItemMutation.Variables.Builder.() -> Unit = {}
  
): com.google.firebase.dataconnect.MutationRef<
    UpdateDonationItemMutation.Data,
    UpdateDonationItemMutation.Variables
  > =
  ref(
    
      UpdateDonationItemMutation.Variables.build(
        donationId=donationId,lotId=lotId,
  
    block_
      )
    
  )

public suspend fun UpdateDonationItemMutation.execute(
  
    donationId: java.util.UUID,lotId: java.util.UUID,
  
    block_: UpdateDonationItemMutation.Variables.Builder.() -> Unit = {}
  
  ): com.google.firebase.dataconnect.MutationResult<
    UpdateDonationItemMutation.Data,
    UpdateDonationItemMutation.Variables
  > =
  ref(
    
      donationId=donationId,lotId=lotId,
  
    block_
    
  ).execute()


