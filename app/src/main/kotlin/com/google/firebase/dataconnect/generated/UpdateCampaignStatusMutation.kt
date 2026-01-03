
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



public interface UpdateCampaignStatusMutation :
    com.google.firebase.dataconnect.generated.GeneratedMutation<
      SasConnectorConnector,
      UpdateCampaignStatusMutation.Data,
      UpdateCampaignStatusMutation.Variables
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
  
    val campaign_update: CampaignKey?
  ) {
    
    
  }
  

  public companion object {
    public val operationName: String = "UpdateCampaignStatus"

    public val dataDeserializer: kotlinx.serialization.DeserializationStrategy<Data> =
      kotlinx.serialization.serializer()

    public val variablesSerializer: kotlinx.serialization.SerializationStrategy<Variables> =
      kotlinx.serialization.serializer()
  }
}

public fun UpdateCampaignStatusMutation.ref(
  
    id: java.util.UUID,statusId: StatusTypeKey,
  
  
): com.google.firebase.dataconnect.MutationRef<
    UpdateCampaignStatusMutation.Data,
    UpdateCampaignStatusMutation.Variables
  > =
  ref(
    
      UpdateCampaignStatusMutation.Variables(
        id=id,statusId=statusId,
  
      )
    
  )

public suspend fun UpdateCampaignStatusMutation.execute(
  
    id: java.util.UUID,statusId: StatusTypeKey,
  
  
  ): com.google.firebase.dataconnect.MutationResult<
    UpdateCampaignStatusMutation.Data,
    UpdateCampaignStatusMutation.Variables
  > =
  ref(
    
      id=id,statusId=statusId,
  
    
  ).execute()


