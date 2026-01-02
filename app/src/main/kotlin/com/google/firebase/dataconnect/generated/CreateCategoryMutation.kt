
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



public interface CreateCategoryMutation :
    com.google.firebase.dataconnect.generated.GeneratedMutation<
      SasConnectorConnector,
      CreateCategoryMutation.Data,
      CreateCategoryMutation.Variables
    >
{
  
    @kotlinx.serialization.Serializable
  public data class Variables(
  
    val name: String,
    val description: com.google.firebase.dataconnect.OptionalVariable<String?>,
    val active: com.google.firebase.dataconnect.OptionalVariable<Boolean?>
  ) {
    
    
      
      @kotlin.DslMarker public annotation class BuilderDsl

      @BuilderDsl
      public interface Builder {
        public var name: String
        public var description: String?
        public var active: Boolean?
        
      }

      public companion object {
        @Suppress("NAME_SHADOWING")
        public fun build(
          name: String,
          block_: Builder.() -> Unit
        ): Variables {
          var name= name
            var description: com.google.firebase.dataconnect.OptionalVariable<String?> =
                com.google.firebase.dataconnect.OptionalVariable.Undefined
            var active: com.google.firebase.dataconnect.OptionalVariable<Boolean?> =
                com.google.firebase.dataconnect.OptionalVariable.Undefined
            

          return object : Builder {
            override var name: String
              get() = throw UnsupportedOperationException("getting builder values is not supported")
              set(value_) { name = value_ }
              
            override var description: String?
              get() = throw UnsupportedOperationException("getting builder values is not supported")
              set(value_) { description = com.google.firebase.dataconnect.OptionalVariable.Value(value_) }
              
            override var active: Boolean?
              get() = throw UnsupportedOperationException("getting builder values is not supported")
              set(value_) { active = com.google.firebase.dataconnect.OptionalVariable.Value(value_) }
              
            
          }.apply(block_)
          .let {
            Variables(
              name=name,description=description,active=active,
            )
          }
        }
      }
    
  }
  

  
    @kotlinx.serialization.Serializable
  public data class Data(
  
    val category_insert: CategoryKey
  ) {
    
    
  }
  

  public companion object {
    public val operationName: String = "CreateCategory"

    public val dataDeserializer: kotlinx.serialization.DeserializationStrategy<Data> =
      kotlinx.serialization.serializer()

    public val variablesSerializer: kotlinx.serialization.SerializationStrategy<Variables> =
      kotlinx.serialization.serializer()
  }
}

public fun CreateCategoryMutation.ref(
  
    name: String,
  
    block_: CreateCategoryMutation.Variables.Builder.() -> Unit = {}
  
): com.google.firebase.dataconnect.MutationRef<
    CreateCategoryMutation.Data,
    CreateCategoryMutation.Variables
  > =
  ref(
    
      CreateCategoryMutation.Variables.build(
        name=name,
  
    block_
      )
    
  )

public suspend fun CreateCategoryMutation.execute(
  
    name: String,
  
    block_: CreateCategoryMutation.Variables.Builder.() -> Unit = {}
  
  ): com.google.firebase.dataconnect.MutationResult<
    CreateCategoryMutation.Data,
    CreateCategoryMutation.Variables
  > =
  ref(
    
      name=name,
  
    block_
    
  ).execute()


