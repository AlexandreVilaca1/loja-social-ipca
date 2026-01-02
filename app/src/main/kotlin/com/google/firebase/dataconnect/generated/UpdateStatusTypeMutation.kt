
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



public interface UpdateStatusTypeMutation :
    com.google.firebase.dataconnect.generated.GeneratedMutation<
      SasConnectorConnector,
      UpdateStatusTypeMutation.Data,
      UpdateStatusTypeMutation.Variables
    >
{
  
    @kotlinx.serialization.Serializable
  public data class Variables(
  
    val id: @kotlinx.serialization.Serializable(with = com.google.firebase.dataconnect.serializers.UUIDSerializer::class) java.util.UUID,
    val code: com.google.firebase.dataconnect.OptionalVariable<String?>,
    val description: com.google.firebase.dataconnect.OptionalVariable<String?>,
    val sortOrder: com.google.firebase.dataconnect.OptionalVariable<Int?>,
    val color: com.google.firebase.dataconnect.OptionalVariable<String?>,
    val active: com.google.firebase.dataconnect.OptionalVariable<Boolean?>
  ) {
    
    
      
      @kotlin.DslMarker public annotation class BuilderDsl

      @BuilderDsl
      public interface Builder {
        public var id: java.util.UUID
        public var code: String?
        public var description: String?
        public var sortOrder: Int?
        public var color: String?
        public var active: Boolean?
        
      }

      public companion object {
        @Suppress("NAME_SHADOWING")
        public fun build(
          id: java.util.UUID,
          block_: Builder.() -> Unit
        ): Variables {
          var id= id
            var code: com.google.firebase.dataconnect.OptionalVariable<String?> =
                com.google.firebase.dataconnect.OptionalVariable.Undefined
            var description: com.google.firebase.dataconnect.OptionalVariable<String?> =
                com.google.firebase.dataconnect.OptionalVariable.Undefined
            var sortOrder: com.google.firebase.dataconnect.OptionalVariable<Int?> =
                com.google.firebase.dataconnect.OptionalVariable.Undefined
            var color: com.google.firebase.dataconnect.OptionalVariable<String?> =
                com.google.firebase.dataconnect.OptionalVariable.Undefined
            var active: com.google.firebase.dataconnect.OptionalVariable<Boolean?> =
                com.google.firebase.dataconnect.OptionalVariable.Undefined
            

          return object : Builder {
            override var id: java.util.UUID
              get() = throw UnsupportedOperationException("getting builder values is not supported")
              set(value_) { id = value_ }
              
            override var code: String?
              get() = throw UnsupportedOperationException("getting builder values is not supported")
              set(value_) { code = com.google.firebase.dataconnect.OptionalVariable.Value(value_) }
              
            override var description: String?
              get() = throw UnsupportedOperationException("getting builder values is not supported")
              set(value_) { description = com.google.firebase.dataconnect.OptionalVariable.Value(value_) }
              
            override var sortOrder: Int?
              get() = throw UnsupportedOperationException("getting builder values is not supported")
              set(value_) { sortOrder = com.google.firebase.dataconnect.OptionalVariable.Value(value_) }
              
            override var color: String?
              get() = throw UnsupportedOperationException("getting builder values is not supported")
              set(value_) { color = com.google.firebase.dataconnect.OptionalVariable.Value(value_) }
              
            override var active: Boolean?
              get() = throw UnsupportedOperationException("getting builder values is not supported")
              set(value_) { active = com.google.firebase.dataconnect.OptionalVariable.Value(value_) }
              
            
          }.apply(block_)
          .let {
            Variables(
              id=id,code=code,description=description,sortOrder=sortOrder,color=color,active=active,
            )
          }
        }
      }
    
  }
  

  
    @kotlinx.serialization.Serializable
  public data class Data(
  
    val statusType_update: StatusTypeKey?
  ) {
    
    
  }
  

  public companion object {
    public val operationName: String = "UpdateStatusType"

    public val dataDeserializer: kotlinx.serialization.DeserializationStrategy<Data> =
      kotlinx.serialization.serializer()

    public val variablesSerializer: kotlinx.serialization.SerializationStrategy<Variables> =
      kotlinx.serialization.serializer()
  }
}

public fun UpdateStatusTypeMutation.ref(
  
    id: java.util.UUID,
  
    block_: UpdateStatusTypeMutation.Variables.Builder.() -> Unit = {}
  
): com.google.firebase.dataconnect.MutationRef<
    UpdateStatusTypeMutation.Data,
    UpdateStatusTypeMutation.Variables
  > =
  ref(
    
      UpdateStatusTypeMutation.Variables.build(
        id=id,
  
    block_
      )
    
  )

public suspend fun UpdateStatusTypeMutation.execute(
  
    id: java.util.UUID,
  
    block_: UpdateStatusTypeMutation.Variables.Builder.() -> Unit = {}
  
  ): com.google.firebase.dataconnect.MutationResult<
    UpdateStatusTypeMutation.Data,
    UpdateStatusTypeMutation.Variables
  > =
  ref(
    
      id=id,
  
    block_
    
  ).execute()


