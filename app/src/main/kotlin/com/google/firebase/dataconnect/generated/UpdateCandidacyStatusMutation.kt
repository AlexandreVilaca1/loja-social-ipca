
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



public interface UpdateCandidacyStatusMutation :
    com.google.firebase.dataconnect.generated.GeneratedMutation<
      SasConnectorConnector,
      UpdateCandidacyStatusMutation.Data,
      UpdateCandidacyStatusMutation.Variables
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
  
    val candidacy_update: CandidacyKey?
  ) {
    
    
  }
  

  public companion object {
    public val operationName: String = "UpdateCandidacyStatus"

    public val dataDeserializer: kotlinx.serialization.DeserializationStrategy<Data> =
      kotlinx.serialization.serializer()

    public val variablesSerializer: kotlinx.serialization.SerializationStrategy<Variables> =
      kotlinx.serialization.serializer()
  }
}

public fun UpdateCandidacyStatusMutation.ref(
  
    id: java.util.UUID,statusId: java.util.UUID,
  
  
): com.google.firebase.dataconnect.MutationRef<
    UpdateCandidacyStatusMutation.Data,
    UpdateCandidacyStatusMutation.Variables
  > =
  ref(
    
      UpdateCandidacyStatusMutation.Variables(
        id=id,statusId=statusId,
  
      )
    
  )

public suspend fun UpdateCandidacyStatusMutation.execute(
  
    id: java.util.UUID,statusId: java.util.UUID,
  
  
  ): com.google.firebase.dataconnect.MutationResult<
    UpdateCandidacyStatusMutation.Data,
    UpdateCandidacyStatusMutation.Variables
  > =
  ref(
    
      id=id,statusId=statusId,
  
    
  ).execute()


