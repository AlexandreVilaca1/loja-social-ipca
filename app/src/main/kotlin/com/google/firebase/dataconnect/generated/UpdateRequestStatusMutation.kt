
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



public interface UpdateRequestStatusMutation :
    com.google.firebase.dataconnect.generated.GeneratedMutation<
      SasConnectorConnector,
      UpdateRequestStatusMutation.Data,
      UpdateRequestStatusMutation.Variables
    >
{
  
    @kotlinx.serialization.Serializable
  public data class Variables(
  
    val id: @kotlinx.serialization.Serializable(with = com.google.firebase.dataconnect.serializers.UUIDSerializer::class) java.util.UUID,
    val statusId: @kotlinx.serialization.Serializable(with = com.google.firebase.dataconnect.serializers.UUIDSerializer::class) java.util.UUID
  ) {
    
    
  }
  

  
    @kotlinx.serialization.Serializable
  public data class Data(
  
    val request_update: RequestKey?
  ) {
    
    
  }
  

  public companion object {
    public val operationName: String = "UpdateRequestStatus"

    public val dataDeserializer: kotlinx.serialization.DeserializationStrategy<Data> =
      kotlinx.serialization.serializer()

    public val variablesSerializer: kotlinx.serialization.SerializationStrategy<Variables> =
      kotlinx.serialization.serializer()
  }
}

public fun UpdateRequestStatusMutation.ref(
  
    id: java.util.UUID,statusId: java.util.UUID,
  
  
): com.google.firebase.dataconnect.MutationRef<
    UpdateRequestStatusMutation.Data,
    UpdateRequestStatusMutation.Variables
  > =
  ref(
    
      UpdateRequestStatusMutation.Variables(
        id=id,statusId=statusId,
  
      )
    
  )

public suspend fun UpdateRequestStatusMutation.execute(
  
    id: java.util.UUID,statusId: java.util.UUID,
  
  
  ): com.google.firebase.dataconnect.MutationResult<
    UpdateRequestStatusMutation.Data,
    UpdateRequestStatusMutation.Variables
  > =
  ref(
    
      id=id,statusId=statusId,
  
    
  ).execute()


