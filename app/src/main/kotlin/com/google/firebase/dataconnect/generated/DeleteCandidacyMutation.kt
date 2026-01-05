
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



public interface DeleteCandidacyMutation :
    com.google.firebase.dataconnect.generated.GeneratedMutation<
      SasConnectorConnector,
      DeleteCandidacyMutation.Data,
      DeleteCandidacyMutation.Variables
    >
{
  
    @kotlinx.serialization.Serializable
  public data class Variables(
  
    val id: @kotlinx.serialization.Serializable(with = com.google.firebase.dataconnect.serializers.UUIDSerializer::class) java.util.UUID
  ) {
    
    
  }
  

  
    @kotlinx.serialization.Serializable
  public data class Data(
  
    val candidacy_delete: CandidacyKey?
  ) {
    
    
  }
  

  public companion object {
    public val operationName: String = "DeleteCandidacy"

    public val dataDeserializer: kotlinx.serialization.DeserializationStrategy<Data> =
      kotlinx.serialization.serializer()

    public val variablesSerializer: kotlinx.serialization.SerializationStrategy<Variables> =
      kotlinx.serialization.serializer()
  }
}

public fun DeleteCandidacyMutation.ref(
  
    id: java.util.UUID,
  
  
): com.google.firebase.dataconnect.MutationRef<
    DeleteCandidacyMutation.Data,
    DeleteCandidacyMutation.Variables
  > =
  ref(
    
      DeleteCandidacyMutation.Variables(
        id=id,
  
      )
    
  )

public suspend fun DeleteCandidacyMutation.execute(
  
    id: java.util.UUID,
  
  
  ): com.google.firebase.dataconnect.MutationResult<
    DeleteCandidacyMutation.Data,
    DeleteCandidacyMutation.Variables
  > =
  ref(
    
      id=id,
  
    
  ).execute()


