
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



public interface UpdateDistributionStatusMutation :
    com.google.firebase.dataconnect.generated.GeneratedMutation<
      SasConnectorConnector,
      UpdateDistributionStatusMutation.Data,
      UpdateDistributionStatusMutation.Variables
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
  
    val distribution_update: DistributionKey?
  ) {
    
    
  }
  

  public companion object {
    public val operationName: String = "UpdateDistributionStatus"

    public val dataDeserializer: kotlinx.serialization.DeserializationStrategy<Data> =
      kotlinx.serialization.serializer()

    public val variablesSerializer: kotlinx.serialization.SerializationStrategy<Variables> =
      kotlinx.serialization.serializer()
  }
}

public fun UpdateDistributionStatusMutation.ref(
  
    id: java.util.UUID,statusId: StatusTypeKey,
  
  
): com.google.firebase.dataconnect.MutationRef<
    UpdateDistributionStatusMutation.Data,
    UpdateDistributionStatusMutation.Variables
  > =
  ref(
    
      UpdateDistributionStatusMutation.Variables(
        id=id,statusId=statusId,
  
      )
    
  )

public suspend fun UpdateDistributionStatusMutation.execute(
  
    id: java.util.UUID,statusId: StatusTypeKey,
  
  
  ): com.google.firebase.dataconnect.MutationResult<
    UpdateDistributionStatusMutation.Data,
    UpdateDistributionStatusMutation.Variables
  > =
  ref(
    
      id=id,statusId=statusId,
  
    
  ).execute()


