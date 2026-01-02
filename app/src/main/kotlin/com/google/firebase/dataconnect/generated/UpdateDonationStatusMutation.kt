
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



public interface UpdateDonationStatusMutation :
    com.google.firebase.dataconnect.generated.GeneratedMutation<
      SasConnectorConnector,
      UpdateDonationStatusMutation.Data,
      UpdateDonationStatusMutation.Variables
    >
{
  
    @kotlinx.serialization.Serializable
  public data class Variables(
  
    val id: @kotlinx.serialization.Serializable(with = com.google.firebase.dataconnect.serializers.UUIDSerializer::class) java.util.UUID,
    val statusId: StatusTypeKey
  ) {
    
    
  }
  

  
    @kotlinx.serialization.Serializable
  public data class Data(
  
    val donation_update: DonationKey?
  ) {
    
    
  }
  

  public companion object {
    public val operationName: String = "UpdateDonationStatus"

    public val dataDeserializer: kotlinx.serialization.DeserializationStrategy<Data> =
      kotlinx.serialization.serializer()

    public val variablesSerializer: kotlinx.serialization.SerializationStrategy<Variables> =
      kotlinx.serialization.serializer()
  }
}

public fun UpdateDonationStatusMutation.ref(
  
    id: java.util.UUID,statusId: StatusTypeKey,
  
  
): com.google.firebase.dataconnect.MutationRef<
    UpdateDonationStatusMutation.Data,
    UpdateDonationStatusMutation.Variables
  > =
  ref(
    
      UpdateDonationStatusMutation.Variables(
        id=id,statusId=statusId,
  
      )
    
  )

public suspend fun UpdateDonationStatusMutation.execute(
  
    id: java.util.UUID,statusId: StatusTypeKey,
  
  
  ): com.google.firebase.dataconnect.MutationResult<
    UpdateDonationStatusMutation.Data,
    UpdateDonationStatusMutation.Variables
  > =
  ref(
    
      id=id,statusId=statusId,
  
    
  ).execute()


