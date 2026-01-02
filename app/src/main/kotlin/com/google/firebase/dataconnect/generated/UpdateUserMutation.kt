
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



public interface UpdateUserMutation :
    com.google.firebase.dataconnect.generated.GeneratedMutation<
      SasConnectorConnector,
      UpdateUserMutation.Data,
      UpdateUserMutation.Variables
    >
{
  
    @kotlinx.serialization.Serializable
  public data class Variables(
  
    val id: @kotlinx.serialization.Serializable(with = com.google.firebase.dataconnect.serializers.UUIDSerializer::class) java.util.UUID,
    val name: com.google.firebase.dataconnect.OptionalVariable<String?>,
    val email: com.google.firebase.dataconnect.OptionalVariable<String?>,
    val role: com.google.firebase.dataconnect.OptionalVariable<String?>
  ) {
    
    
      
      @kotlin.DslMarker public annotation class BuilderDsl

      @BuilderDsl
      public interface Builder {
        public var id: java.util.UUID
        public var name: String?
        public var email: String?
        public var role: String?
        
      }

      public companion object {
        @Suppress("NAME_SHADOWING")
        public fun build(
          id: java.util.UUID,
          block_: Builder.() -> Unit
        ): Variables {
          var id= id
            var name: com.google.firebase.dataconnect.OptionalVariable<String?> =
                com.google.firebase.dataconnect.OptionalVariable.Undefined
            var email: com.google.firebase.dataconnect.OptionalVariable<String?> =
                com.google.firebase.dataconnect.OptionalVariable.Undefined
            var role: com.google.firebase.dataconnect.OptionalVariable<String?> =
                com.google.firebase.dataconnect.OptionalVariable.Undefined
            

          return object : Builder {
            override var id: java.util.UUID
              get() = throw UnsupportedOperationException("getting builder values is not supported")
              set(value_) { id = value_ }
              
            override var name: String?
              get() = throw UnsupportedOperationException("getting builder values is not supported")
              set(value_) { name = com.google.firebase.dataconnect.OptionalVariable.Value(value_) }
              
            override var email: String?
              get() = throw UnsupportedOperationException("getting builder values is not supported")
              set(value_) { email = com.google.firebase.dataconnect.OptionalVariable.Value(value_) }
              
            override var role: String?
              get() = throw UnsupportedOperationException("getting builder values is not supported")
              set(value_) { role = com.google.firebase.dataconnect.OptionalVariable.Value(value_) }
              
            
          }.apply(block_)
          .let {
            Variables(
              id=id,name=name,email=email,role=role,
            )
          }
        }
      }
    
  }
  

  
    @kotlinx.serialization.Serializable
  public data class Data(
  
    val user_update: UserKey?
  ) {
    
    
  }
  

  public companion object {
    public val operationName: String = "UpdateUser"

    public val dataDeserializer: kotlinx.serialization.DeserializationStrategy<Data> =
      kotlinx.serialization.serializer()

    public val variablesSerializer: kotlinx.serialization.SerializationStrategy<Variables> =
      kotlinx.serialization.serializer()
  }
}

public fun UpdateUserMutation.ref(
  
    id: java.util.UUID,
  
    block_: UpdateUserMutation.Variables.Builder.() -> Unit = {}
  
): com.google.firebase.dataconnect.MutationRef<
    UpdateUserMutation.Data,
    UpdateUserMutation.Variables
  > =
  ref(
    
      UpdateUserMutation.Variables.build(
        id=id,
  
    block_
      )
    
  )

public suspend fun UpdateUserMutation.execute(
  
    id: java.util.UUID,
  
    block_: UpdateUserMutation.Variables.Builder.() -> Unit = {}
  
  ): com.google.firebase.dataconnect.MutationResult<
    UpdateUserMutation.Data,
    UpdateUserMutation.Variables
  > =
  ref(
    
      id=id,
  
    block_
    
  ).execute()


