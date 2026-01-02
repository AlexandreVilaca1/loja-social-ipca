
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



public interface CreateStatusTypeMutation :
    com.google.firebase.dataconnect.generated.GeneratedMutation<
      SasConnectorConnector,
      CreateStatusTypeMutation.Data,
      CreateStatusTypeMutation.Variables
    >
{
  
    @kotlinx.serialization.Serializable
  public data class Variables(
  
    val code: String,
    val description: String,
    val sortOrder: Int,
    val color: com.google.firebase.dataconnect.OptionalVariable<String?>,
    val active: com.google.firebase.dataconnect.OptionalVariable<Boolean?>
  ) {
    
    
      
      @kotlin.DslMarker public annotation class BuilderDsl

      @BuilderDsl
      public interface Builder {
        public var code: String
        public var description: String
        public var sortOrder: Int
        public var color: String?
        public var active: Boolean?
        
      }

      public companion object {
        @Suppress("NAME_SHADOWING")
        public fun build(
          code: String,description: String,sortOrder: Int,
          block_: Builder.() -> Unit
        ): Variables {
          var code= code
            var description= description
            var sortOrder= sortOrder
            var color: com.google.firebase.dataconnect.OptionalVariable<String?> =
                com.google.firebase.dataconnect.OptionalVariable.Undefined
            var active: com.google.firebase.dataconnect.OptionalVariable<Boolean?> =
                com.google.firebase.dataconnect.OptionalVariable.Undefined
            

          return object : Builder {
            override var code: String
              get() = throw UnsupportedOperationException("getting builder values is not supported")
              set(value_) { code = value_ }
              
            override var description: String
              get() = throw UnsupportedOperationException("getting builder values is not supported")
              set(value_) { description = value_ }
              
            override var sortOrder: Int
              get() = throw UnsupportedOperationException("getting builder values is not supported")
              set(value_) { sortOrder = value_ }
              
            override var color: String?
              get() = throw UnsupportedOperationException("getting builder values is not supported")
              set(value_) { color = com.google.firebase.dataconnect.OptionalVariable.Value(value_) }
              
            override var active: Boolean?
              get() = throw UnsupportedOperationException("getting builder values is not supported")
              set(value_) { active = com.google.firebase.dataconnect.OptionalVariable.Value(value_) }
              
            
          }.apply(block_)
          .let {
            Variables(
              code=code,description=description,sortOrder=sortOrder,color=color,active=active,
            )
          }
        }
      }
    
  }
  

  
    @kotlinx.serialization.Serializable
  public data class Data(
  
    val statusType_insert: StatusTypeKey
  ) {
    
    
  }
  

  public companion object {
    public val operationName: String = "CreateStatusType"

    public val dataDeserializer: kotlinx.serialization.DeserializationStrategy<Data> =
      kotlinx.serialization.serializer()

    public val variablesSerializer: kotlinx.serialization.SerializationStrategy<Variables> =
      kotlinx.serialization.serializer()
  }
}

public fun CreateStatusTypeMutation.ref(
  
    code: String,description: String,sortOrder: Int,
  
    block_: CreateStatusTypeMutation.Variables.Builder.() -> Unit = {}
  
): com.google.firebase.dataconnect.MutationRef<
    CreateStatusTypeMutation.Data,
    CreateStatusTypeMutation.Variables
  > =
  ref(
    
      CreateStatusTypeMutation.Variables.build(
        code=code,description=description,sortOrder=sortOrder,
  
    block_
      )
    
  )

public suspend fun CreateStatusTypeMutation.execute(
  
    code: String,description: String,sortOrder: Int,
  
    block_: CreateStatusTypeMutation.Variables.Builder.() -> Unit = {}
  
  ): com.google.firebase.dataconnect.MutationResult<
    CreateStatusTypeMutation.Data,
    CreateStatusTypeMutation.Variables
  > =
  ref(
    
      code=code,description=description,sortOrder=sortOrder,
  
    block_
    
  ).execute()


