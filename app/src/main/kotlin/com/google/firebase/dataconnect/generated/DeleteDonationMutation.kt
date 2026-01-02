
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



public interface DeleteDonationMutation :
    com.google.firebase.dataconnect.generated.GeneratedMutation<
      SasConnectorConnector,
      DeleteDonationMutation.Data,
      DeleteDonationMutation.Variables
    >
{
  
    @kotlinx.serialization.Serializable
  public data class Variables(
  
    val id: @kotlinx.serialization.Serializable(with = com.google.firebase.dataconnect.serializers.UUIDSerializer::class) java.util.UUID
  ) {
    
    
  }
  

  
    @kotlinx.serialization.Serializable
  public data class Data(
  
    val donation_delete: DonationKey?
  ) {
    
    
  }
  

  public companion object {
    public val operationName: String = "DeleteDonation"

    public val dataDeserializer: kotlinx.serialization.DeserializationStrategy<Data> =
      kotlinx.serialization.serializer()

    public val variablesSerializer: kotlinx.serialization.SerializationStrategy<Variables> =
      kotlinx.serialization.serializer()
  }
}

public fun DeleteDonationMutation.ref(
  
    id: java.util.UUID,
  
  
): com.google.firebase.dataconnect.MutationRef<
    DeleteDonationMutation.Data,
    DeleteDonationMutation.Variables
  > =
  ref(
    
      DeleteDonationMutation.Variables(
        id=id,
  
      )
    
  )

public suspend fun DeleteDonationMutation.execute(
  
    id: java.util.UUID,
  
  
  ): com.google.firebase.dataconnect.MutationResult<
    DeleteDonationMutation.Data,
    DeleteDonationMutation.Variables
  > =
  ref(
    
      id=id,
  
    
  ).execute()


