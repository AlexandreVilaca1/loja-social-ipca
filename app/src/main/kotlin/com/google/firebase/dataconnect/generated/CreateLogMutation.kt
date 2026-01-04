
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



public interface CreateLogMutation :
    com.google.firebase.dataconnect.generated.GeneratedMutation<
      SasConnectorConnector,
      CreateLogMutation.Data,
      CreateLogMutation.Variables
    >
{
  
    @kotlinx.serialization.Serializable
  public data class Variables(
  
    val description: String,
    val logTypeId: @kotlinx.serialization.Serializable(with = com.google.firebase.dataconnect.serializers.UUIDSerializer::class) java.util.UUID,
    val userId: @kotlinx.serialization.Serializable(with = com.google.firebase.dataconnect.serializers.UUIDSerializer::class) java.util.UUID
  ) {
    
    
  }
  

  
    @kotlinx.serialization.Serializable
  public data class Data(
  
    val log_insert: LogKey
  ) {
    
    
  }
  

  public companion object {
    public val operationName: String = "CreateLog"

    public val dataDeserializer: kotlinx.serialization.DeserializationStrategy<Data> =
      kotlinx.serialization.serializer()

    public val variablesSerializer: kotlinx.serialization.SerializationStrategy<Variables> =
      kotlinx.serialization.serializer()
  }
}

public fun CreateLogMutation.ref(
  
    description: String,logTypeId: java.util.UUID,userId: java.util.UUID,
  
  
): com.google.firebase.dataconnect.MutationRef<
    CreateLogMutation.Data,
    CreateLogMutation.Variables
  > =
  ref(
    
      CreateLogMutation.Variables(
        description=description,logTypeId=logTypeId,userId=userId,
  
      )
    
  )

public suspend fun CreateLogMutation.execute(
  
    description: String,logTypeId: java.util.UUID,userId: java.util.UUID,
  
  
  ): com.google.firebase.dataconnect.MutationResult<
    CreateLogMutation.Data,
    CreateLogMutation.Variables
  > =
  ref(
    
      description=description,logTypeId=logTypeId,userId=userId,
  
    
  ).execute()


