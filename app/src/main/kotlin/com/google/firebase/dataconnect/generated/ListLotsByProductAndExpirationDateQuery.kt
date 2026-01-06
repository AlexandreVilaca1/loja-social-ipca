
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


public interface ListLotsByProductAndExpirationDateQuery :
    com.google.firebase.dataconnect.generated.GeneratedQuery<
      SasConnectorConnector,
      ListLotsByProductAndExpirationDateQuery.Data,
      ListLotsByProductAndExpirationDateQuery.Variables
    >
{
  
    @kotlinx.serialization.Serializable
  public data class Variables(
  
    val productId: @kotlinx.serialization.Serializable(with = com.google.firebase.dataconnect.serializers.UUIDSerializer::class) java.util.UUID,
    val expirationDate: com.google.firebase.dataconnect.OptionalVariable<com.google.firebase.dataconnect.LocalDate?>,
    val limit: com.google.firebase.dataconnect.OptionalVariable<Int?>,
    val offset: com.google.firebase.dataconnect.OptionalVariable<Int?>
  ) {
    
    
      
      @kotlin.DslMarker public annotation class BuilderDsl

      @BuilderDsl
      public interface Builder {
        public var productId: java.util.UUID
        public var expirationDate: com.google.firebase.dataconnect.LocalDate?
        public var limit: Int?
        public var offset: Int?
        
      }

      public companion object {
        @Suppress("NAME_SHADOWING")
        public fun build(
          productId: java.util.UUID,
          block_: Builder.() -> Unit
        ): Variables {
          var productId= productId
            var expirationDate: com.google.firebase.dataconnect.OptionalVariable<com.google.firebase.dataconnect.LocalDate?> =
                com.google.firebase.dataconnect.OptionalVariable.Undefined
            var limit: com.google.firebase.dataconnect.OptionalVariable<Int?> =
                com.google.firebase.dataconnect.OptionalVariable.Undefined
            var offset: com.google.firebase.dataconnect.OptionalVariable<Int?> =
                com.google.firebase.dataconnect.OptionalVariable.Undefined
            

          return object : Builder {
            override var productId: java.util.UUID
              get() = throw UnsupportedOperationException("getting builder values is not supported")
              set(value_) { productId = value_ }
              
            override var expirationDate: com.google.firebase.dataconnect.LocalDate?
              get() = throw UnsupportedOperationException("getting builder values is not supported")
              set(value_) { expirationDate = com.google.firebase.dataconnect.OptionalVariable.Value(value_) }
              
            override var limit: Int?
              get() = throw UnsupportedOperationException("getting builder values is not supported")
              set(value_) { limit = com.google.firebase.dataconnect.OptionalVariable.Value(value_) }
              
            override var offset: Int?
              get() = throw UnsupportedOperationException("getting builder values is not supported")
              set(value_) { offset = com.google.firebase.dataconnect.OptionalVariable.Value(value_) }
              
            
          }.apply(block_)
          .let {
            Variables(
              productId=productId,expirationDate=expirationDate,limit=limit,offset=offset,
            )
          }
        }
      }
    
  }
  

  
    @kotlinx.serialization.Serializable
  public data class Data(
  
    val lots: List<LotsItem>
  ) {
    
      
        @kotlinx.serialization.Serializable
  public data class LotsItem(
  
    val id: @kotlinx.serialization.Serializable(with = com.google.firebase.dataconnect.serializers.UUIDSerializer::class) java.util.UUID,
    val initialQuantity: Int,
    val currentQuantity: Int,
    val entryDate: com.google.firebase.dataconnect.LocalDate,
    val lotCode: String,
    val expirationDate: com.google.firebase.dataconnect.LocalDate?,
    val observations: String?,
    val product: Product?,
    val createdAt: @kotlinx.serialization.Serializable(with = com.google.firebase.dataconnect.serializers.TimestampSerializer::class) com.google.firebase.Timestamp,
    val updatedAt: @kotlinx.serialization.Serializable(with = com.google.firebase.dataconnect.serializers.TimestampSerializer::class) com.google.firebase.Timestamp
  ) {
    
      
        @kotlinx.serialization.Serializable
  public data class Product(
  
    val id: @kotlinx.serialization.Serializable(with = com.google.firebase.dataconnect.serializers.UUIDSerializer::class) java.util.UUID,
    val name: String,
    val unitOfMeasure: String
  ) {
    
    
  }
      
    
    
  }
      
    
    
  }
  

  public companion object {
    public val operationName: String = "ListLotsByProductAndExpirationDate"

    public val dataDeserializer: kotlinx.serialization.DeserializationStrategy<Data> =
      kotlinx.serialization.serializer()

    public val variablesSerializer: kotlinx.serialization.SerializationStrategy<Variables> =
      kotlinx.serialization.serializer()
  }
}

public fun ListLotsByProductAndExpirationDateQuery.ref(
  
    productId: java.util.UUID,
  
    block_: ListLotsByProductAndExpirationDateQuery.Variables.Builder.() -> Unit = {}
  
): com.google.firebase.dataconnect.QueryRef<
    ListLotsByProductAndExpirationDateQuery.Data,
    ListLotsByProductAndExpirationDateQuery.Variables
  > =
  ref(
    
      ListLotsByProductAndExpirationDateQuery.Variables.build(
        productId=productId,
  
    block_
      )
    
  )

public suspend fun ListLotsByProductAndExpirationDateQuery.execute(
  
    productId: java.util.UUID,
  
    block_: ListLotsByProductAndExpirationDateQuery.Variables.Builder.() -> Unit = {}
  
  ): com.google.firebase.dataconnect.QueryResult<
    ListLotsByProductAndExpirationDateQuery.Data,
    ListLotsByProductAndExpirationDateQuery.Variables
  > =
  ref(
    
      productId=productId,
  
    block_
    
  ).execute()


  public fun ListLotsByProductAndExpirationDateQuery.flow(
    
      productId: java.util.UUID,
  
    block_: ListLotsByProductAndExpirationDateQuery.Variables.Builder.() -> Unit = {}
    
    ): kotlinx.coroutines.flow.Flow<ListLotsByProductAndExpirationDateQuery.Data> =
    ref(
        
          productId=productId,
  
    block_
        
      ).subscribe()
      .flow
      ._flow_map { querySubscriptionResult -> querySubscriptionResult.result.getOrNull() }
      ._flow_filterNotNull()
      ._flow_map { it.data }

