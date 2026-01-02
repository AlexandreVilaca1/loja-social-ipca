
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



public interface UpdateProductMutation :
    com.google.firebase.dataconnect.generated.GeneratedMutation<
      SasConnectorConnector,
      UpdateProductMutation.Data,
      UpdateProductMutation.Variables
    >
{
  
    @kotlinx.serialization.Serializable
  public data class Variables(
  
    val id: @kotlinx.serialization.Serializable(with = com.google.firebase.dataconnect.serializers.UUIDSerializer::class) java.util.UUID,
    val name: com.google.firebase.dataconnect.OptionalVariable<String?>,
    val unitOfMeasure: com.google.firebase.dataconnect.OptionalVariable<String?>,
    val isActive: com.google.firebase.dataconnect.OptionalVariable<Boolean?>,
    val description: com.google.firebase.dataconnect.OptionalVariable<String?>,
    val categoryId: com.google.firebase.dataconnect.OptionalVariable<CategoryKey?>
  ) {
    
    
      
      @kotlin.DslMarker public annotation class BuilderDsl

      @BuilderDsl
      public interface Builder {
        public var id: java.util.UUID
        public var name: String?
        public var unitOfMeasure: String?
        public var isActive: Boolean?
        public var description: String?
        public var categoryId: CategoryKey?
        
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
            var unitOfMeasure: com.google.firebase.dataconnect.OptionalVariable<String?> =
                com.google.firebase.dataconnect.OptionalVariable.Undefined
            var isActive: com.google.firebase.dataconnect.OptionalVariable<Boolean?> =
                com.google.firebase.dataconnect.OptionalVariable.Undefined
            var description: com.google.firebase.dataconnect.OptionalVariable<String?> =
                com.google.firebase.dataconnect.OptionalVariable.Undefined
            var categoryId: com.google.firebase.dataconnect.OptionalVariable<CategoryKey?> =
                com.google.firebase.dataconnect.OptionalVariable.Undefined
            

          return object : Builder {
            override var id: java.util.UUID
              get() = throw UnsupportedOperationException("getting builder values is not supported")
              set(value_) { id = value_ }
              
            override var name: String?
              get() = throw UnsupportedOperationException("getting builder values is not supported")
              set(value_) { name = com.google.firebase.dataconnect.OptionalVariable.Value(value_) }
              
            override var unitOfMeasure: String?
              get() = throw UnsupportedOperationException("getting builder values is not supported")
              set(value_) { unitOfMeasure = com.google.firebase.dataconnect.OptionalVariable.Value(value_) }
              
            override var isActive: Boolean?
              get() = throw UnsupportedOperationException("getting builder values is not supported")
              set(value_) { isActive = com.google.firebase.dataconnect.OptionalVariable.Value(value_) }
              
            override var description: String?
              get() = throw UnsupportedOperationException("getting builder values is not supported")
              set(value_) { description = com.google.firebase.dataconnect.OptionalVariable.Value(value_) }
              
            override var categoryId: CategoryKey?
              get() = throw UnsupportedOperationException("getting builder values is not supported")
              set(value_) { categoryId = com.google.firebase.dataconnect.OptionalVariable.Value(value_) }
              
            
          }.apply(block_)
          .let {
            Variables(
              id=id,name=name,unitOfMeasure=unitOfMeasure,isActive=isActive,description=description,categoryId=categoryId,
            )
          }
        }
      }
    
  }
  

  
    @kotlinx.serialization.Serializable
  public data class Data(
  
    val product_update: ProductKey?
  ) {
    
    
  }
  

  public companion object {
    public val operationName: String = "UpdateProduct"

    public val dataDeserializer: kotlinx.serialization.DeserializationStrategy<Data> =
      kotlinx.serialization.serializer()

    public val variablesSerializer: kotlinx.serialization.SerializationStrategy<Variables> =
      kotlinx.serialization.serializer()
  }
}

public fun UpdateProductMutation.ref(
  
    id: java.util.UUID,
  
    block_: UpdateProductMutation.Variables.Builder.() -> Unit = {}
  
): com.google.firebase.dataconnect.MutationRef<
    UpdateProductMutation.Data,
    UpdateProductMutation.Variables
  > =
  ref(
    
      UpdateProductMutation.Variables.build(
        id=id,
  
    block_
      )
    
  )

public suspend fun UpdateProductMutation.execute(
  
    id: java.util.UUID,
  
    block_: UpdateProductMutation.Variables.Builder.() -> Unit = {}
  
  ): com.google.firebase.dataconnect.MutationResult<
    UpdateProductMutation.Data,
    UpdateProductMutation.Variables
  > =
  ref(
    
      id=id,
  
    block_
    
  ).execute()


