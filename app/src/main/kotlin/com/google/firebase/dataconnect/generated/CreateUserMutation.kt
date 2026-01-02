
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



public interface CreateUserMutation :
    com.google.firebase.dataconnect.generated.GeneratedMutation<
      SasConnectorConnector,
      CreateUserMutation.Data,
      CreateUserMutation.Variables
    >
{
  
    @kotlinx.serialization.Serializable
  public data class Variables(
  
    val name: String,
    val email: String,
    val passwordHash: String,
    val role: String
  ) {
    
    
  }
  

  
    @kotlinx.serialization.Serializable
  public data class Data(
  
    val user_insert: UserKey
  ) {
    
    
  }
  

  public companion object {
    public val operationName: String = "CreateUser"

    public val dataDeserializer: kotlinx.serialization.DeserializationStrategy<Data> =
      kotlinx.serialization.serializer()

    public val variablesSerializer: kotlinx.serialization.SerializationStrategy<Variables> =
      kotlinx.serialization.serializer()
  }
}

public fun CreateUserMutation.ref(
  
    name: String,email: String,passwordHash: String,role: String,
  
  
): com.google.firebase.dataconnect.MutationRef<
    CreateUserMutation.Data,
    CreateUserMutation.Variables
  > =
  ref(
    
      CreateUserMutation.Variables(
        name=name,email=email,passwordHash=passwordHash,role=role,
  
      )
    
  )

public suspend fun CreateUserMutation.execute(
  
    name: String,email: String,passwordHash: String,role: String,
  
  
  ): com.google.firebase.dataconnect.MutationResult<
    CreateUserMutation.Data,
    CreateUserMutation.Variables
  > =
  ref(
    
      name=name,email=email,passwordHash=passwordHash,role=role,
  
    
  ).execute()


