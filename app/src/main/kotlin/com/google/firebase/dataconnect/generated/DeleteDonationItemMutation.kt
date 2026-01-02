
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



public interface DeleteDonationItemMutation :
    com.google.firebase.dataconnect.generated.GeneratedMutation<
      SasConnectorConnector,
      DeleteDonationItemMutation.Data,
      DeleteDonationItemMutation.Variables
    >
{
  
    @kotlinx.serialization.Serializable
  public data class Variables(
  
    val donationId: @kotlinx.serialization.Serializable(with = com.google.firebase.dataconnect.serializers.UUIDSerializer::class) java.util.UUID,
    val lotId: @kotlinx.serialization.Serializable(with = com.google.firebase.dataconnect.serializers.UUIDSerializer::class) java.util.UUID
  ) {
    
    
  }
  

  
    @kotlinx.serialization.Serializable
  public data class Data(
  
    val donationItem_delete: DonationItemKey?
  ) {
    
    
  }
  

  public companion object {
    public val operationName: String = "DeleteDonationItem"

    public val dataDeserializer: kotlinx.serialization.DeserializationStrategy<Data> =
      kotlinx.serialization.serializer()

    public val variablesSerializer: kotlinx.serialization.SerializationStrategy<Variables> =
      kotlinx.serialization.serializer()
  }
}

public fun DeleteDonationItemMutation.ref(
  
    donationId: java.util.UUID,lotId: java.util.UUID,
  
  
): com.google.firebase.dataconnect.MutationRef<
    DeleteDonationItemMutation.Data,
    DeleteDonationItemMutation.Variables
  > =
  ref(
    
      DeleteDonationItemMutation.Variables(
        donationId=donationId,lotId=lotId,
  
      )
    
  )

public suspend fun DeleteDonationItemMutation.execute(
  
    donationId: java.util.UUID,lotId: java.util.UUID,
  
  
  ): com.google.firebase.dataconnect.MutationResult<
    DeleteDonationItemMutation.Data,
    DeleteDonationItemMutation.Variables
  > =
  ref(
    
      donationId=donationId,lotId=lotId,
  
    
  ).execute()


