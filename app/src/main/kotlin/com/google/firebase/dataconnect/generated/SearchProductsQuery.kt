
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


import kotlinx.coroutines.flow.filterNotNull as _flow_filterNotNull
import kotlinx.coroutines.flow.map as _flow_map


public interface SearchProductsQuery :
    com.google.firebase.dataconnect.generated.GeneratedQuery<
      SasConnectorConnector,
      SearchProductsQuery.Data,
      SearchProductsQuery.Variables
    >
{
  
    @kotlinx.serialization.Serializable
  public data class Variables(
  
    val searchTerm: com.google.firebase.dataconnect.OptionalVariable<String?>,
    val limit: com.google.firebase.dataconnect.OptionalVariable<Int?>,
    val offset: com.google.firebase.dataconnect.OptionalVariable<Int?>
  ) {
    
    
      
      @kotlin.DslMarker public annotation class BuilderDsl

      @BuilderDsl
      public interface Builder {
        public var searchTerm: String?
        public var limit: Int?
        public var offset: Int?
        
      }

      public companion object {
        @Suppress("NAME_SHADOWING")
        public fun build(
          
          block_: Builder.() -> Unit
        ): Variables {
          var searchTerm: com.google.firebase.dataconnect.OptionalVariable<String?> =
                com.google.firebase.dataconnect.OptionalVariable.Undefined
            var limit: com.google.firebase.dataconnect.OptionalVariable<Int?> =
                com.google.firebase.dataconnect.OptionalVariable.Undefined
            var offset: com.google.firebase.dataconnect.OptionalVariable<Int?> =
                com.google.firebase.dataconnect.OptionalVariable.Undefined
            

          return object : Builder {
            override var searchTerm: String?
              get() = throw UnsupportedOperationException("getting builder values is not supported")
              set(value_) { searchTerm = com.google.firebase.dataconnect.OptionalVariable.Value(value_) }
              
            override var limit: Int?
              get() = throw UnsupportedOperationException("getting builder values is not supported")
              set(value_) { limit = com.google.firebase.dataconnect.OptionalVariable.Value(value_) }
              
            override var offset: Int?
              get() = throw UnsupportedOperationException("getting builder values is not supported")
              set(value_) { offset = com.google.firebase.dataconnect.OptionalVariable.Value(value_) }
              
            
          }.apply(block_)
          .let {
            Variables(
              searchTerm=searchTerm,limit=limit,offset=offset,
            )
          }
        }
      }
    
  }
  

  
    @kotlinx.serialization.Serializable
  public data class Data(
  
    val products: List<ProductsItem>
  ) {
    
      
        @kotlinx.serialization.Serializable
  public data class ProductsItem(
  
    val id: @kotlinx.serialization.Serializable(with = com.google.firebase.dataconnect.serializers.UUIDSerializer::class) java.util.UUID,
    val name: String,
    val unitOfMeasure: String,
    val isActive: Boolean,
    val description: String?,
    val category: Category?,
    val createdAt: @kotlinx.serialization.Serializable(with = com.google.firebase.dataconnect.serializers.TimestampSerializer::class) com.google.firebase.Timestamp?,
    val updateAt: @kotlinx.serialization.Serializable(with = com.google.firebase.dataconnect.serializers.TimestampSerializer::class) com.google.firebase.Timestamp?
  ) {
    
      
        @kotlinx.serialization.Serializable
  public data class Category(
  
    val id: @kotlinx.serialization.Serializable(with = com.google.firebase.dataconnect.serializers.UUIDSerializer::class) java.util.UUID,
    val name: String
  ) {
    
    
  }
      
    
    
  }
      
    
    
  }
  

  public companion object {
    public val operationName: String = "SearchProducts"

    public val dataDeserializer: kotlinx.serialization.DeserializationStrategy<Data> =
      kotlinx.serialization.serializer()

    public val variablesSerializer: kotlinx.serialization.SerializationStrategy<Variables> =
      kotlinx.serialization.serializer()
  }
}

public fun SearchProductsQuery.ref(
  
    
  
    block_: SearchProductsQuery.Variables.Builder.() -> Unit = {}
  
): com.google.firebase.dataconnect.QueryRef<
    SearchProductsQuery.Data,
    SearchProductsQuery.Variables
  > =
  ref(
    
      SearchProductsQuery.Variables.build(
        
  
    block_
      )
    
  )

public suspend fun SearchProductsQuery.execute(
  
    
  
    block_: SearchProductsQuery.Variables.Builder.() -> Unit = {}
  
  ): com.google.firebase.dataconnect.QueryResult<
    SearchProductsQuery.Data,
    SearchProductsQuery.Variables
  > =
  ref(
    
      
  
    block_
    
  ).execute()


  public fun SearchProductsQuery.flow(
    
      
  
    block_: SearchProductsQuery.Variables.Builder.() -> Unit = {}
    
    ): kotlinx.coroutines.flow.Flow<SearchProductsQuery.Data> =
    ref(
        
          
  
    block_
        
      ).subscribe()
      .flow
      ._flow_map { querySubscriptionResult -> querySubscriptionResult.result.getOrNull() }
      ._flow_filterNotNull()
      ._flow_map { it.data }

