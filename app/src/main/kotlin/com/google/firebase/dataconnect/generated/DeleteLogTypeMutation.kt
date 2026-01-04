
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



public interface DeleteLogTypeMutation :
    com.google.firebase.dataconnect.generated.GeneratedMutation<
      SasConnectorConnector,
      DeleteLogTypeMutation.Data,
      DeleteLogTypeMutation.Variables
    >
{
  
    @kotlinx.serialization.Serializable
  public data class Variables(
  
    val id: @kotlinx.serialization.Serializable(with = com.google.firebase.dataconnect.serializers.UUIDSerializer::class) java.util.UUID
  ) {
    
    
  }
  

  
    @kotlinx.serialization.Serializable
  public data class Data(
  
    val logType_delete: LogTypeKey?
  ) {
    
    
  }
  

  public companion object {
    public val operationName: String = "DeleteLogType"

    public val dataDeserializer: kotlinx.serialization.DeserializationStrategy<Data> =
      kotlinx.serialization.serializer()

    public val variablesSerializer: kotlinx.serialization.SerializationStrategy<Variables> =
      kotlinx.serialization.serializer()
  }
}

public fun DeleteLogTypeMutation.ref(
  
    id: java.util.UUID,
  
  
): com.google.firebase.dataconnect.MutationRef<
    DeleteLogTypeMutation.Data,
    DeleteLogTypeMutation.Variables
  > =
  ref(
    
      DeleteLogTypeMutation.Variables(
        id=id,
  
      )
    
  )

public suspend fun DeleteLogTypeMutation.execute(
  
    id: java.util.UUID,
  
  
  ): com.google.firebase.dataconnect.MutationResult<
    DeleteLogTypeMutation.Data,
    DeleteLogTypeMutation.Variables
  > =
  ref(
    
      id=id,
  
    
  ).execute()


