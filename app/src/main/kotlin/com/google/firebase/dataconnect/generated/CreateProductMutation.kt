
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



public interface CreateProductMutation :
    com.google.firebase.dataconnect.generated.GeneratedMutation<
      SasConnectorConnector,
      CreateProductMutation.Data,
      CreateProductMutation.Variables
    >
{
  
    @kotlinx.serialization.Serializable
  public data class Variables(
  
    val name: String,
    val unitOfMeasure: String,
    val isActive: Boolean,
    val description: com.google.firebase.dataconnect.OptionalVariable<String?>,
    val categoryId: com.google.firebase.dataconnect.OptionalVariable<CategoryKey?>
  ) {
    
    
      
      @kotlin.DslMarker public annotation class BuilderDsl

      @BuilderDsl
      public interface Builder {
        public var name: String
        public var unitOfMeasure: String
        public var isActive: Boolean
        public var description: String?
        public var categoryId: CategoryKey?
        
      }

      public companion object {
        @Suppress("NAME_SHADOWING")
        public fun build(
          name: String,unitOfMeasure: String,isActive: Boolean,
          block_: Builder.() -> Unit
        ): Variables {
          var name= name
            var unitOfMeasure= unitOfMeasure
            var isActive= isActive
            var description: com.google.firebase.dataconnect.OptionalVariable<String?> =
                com.google.firebase.dataconnect.OptionalVariable.Undefined
            var categoryId: com.google.firebase.dataconnect.OptionalVariable<CategoryKey?> =
                com.google.firebase.dataconnect.OptionalVariable.Undefined
            

          return object : Builder {
            override var name: String
              get() = throw UnsupportedOperationException("getting builder values is not supported")
              set(value_) { name = value_ }
              
            override var unitOfMeasure: String
              get() = throw UnsupportedOperationException("getting builder values is not supported")
              set(value_) { unitOfMeasure = value_ }
              
            override var isActive: Boolean
              get() = throw UnsupportedOperationException("getting builder values is not supported")
              set(value_) { isActive = value_ }
              
            override var description: String?
              get() = throw UnsupportedOperationException("getting builder values is not supported")
              set(value_) { description = com.google.firebase.dataconnect.OptionalVariable.Value(value_) }
              
            override var categoryId: CategoryKey?
              get() = throw UnsupportedOperationException("getting builder values is not supported")
              set(value_) { categoryId = com.google.firebase.dataconnect.OptionalVariable.Value(value_) }
              
            
          }.apply(block_)
          .let {
            Variables(
              name=name,unitOfMeasure=unitOfMeasure,isActive=isActive,description=description,categoryId=categoryId,
            )
          }
        }
      }
    
  }
  

  
    @kotlinx.serialization.Serializable
  public data class Data(
  
    val product_insert: ProductKey
  ) {
    
    
  }
  

  public companion object {
    public val operationName: String = "CreateProduct"

    public val dataDeserializer: kotlinx.serialization.DeserializationStrategy<Data> =
      kotlinx.serialization.serializer()

    public val variablesSerializer: kotlinx.serialization.SerializationStrategy<Variables> =
      kotlinx.serialization.serializer()
  }
}

public fun CreateProductMutation.ref(
  
    name: String,unitOfMeasure: String,isActive: Boolean,
  
    block_: CreateProductMutation.Variables.Builder.() -> Unit = {}
  
): com.google.firebase.dataconnect.MutationRef<
    CreateProductMutation.Data,
    CreateProductMutation.Variables
  > =
  ref(
    
      CreateProductMutation.Variables.build(
        name=name,unitOfMeasure=unitOfMeasure,isActive=isActive,
  
    block_
      )
    
  )

public suspend fun CreateProductMutation.execute(
  
    name: String,unitOfMeasure: String,isActive: Boolean,
  
    block_: CreateProductMutation.Variables.Builder.() -> Unit = {}
  
  ): com.google.firebase.dataconnect.MutationResult<
    CreateProductMutation.Data,
    CreateProductMutation.Variables
  > =
  ref(
    
      name=name,unitOfMeasure=unitOfMeasure,isActive=isActive,
  
    block_
    
  ).execute()


