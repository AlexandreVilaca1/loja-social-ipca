
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


public interface GetProductByIdQuery :
    com.google.firebase.dataconnect.generated.GeneratedQuery<
      SasConnectorConnector,
      GetProductByIdQuery.Data,
      GetProductByIdQuery.Variables
    >
{
  
    @kotlinx.serialization.Serializable
  public data class Variables(
  
    val id: @kotlinx.serialization.Serializable(with = com.google.firebase.dataconnect.serializers.UUIDSerializer::class) java.util.UUID
  ) {
    
    
  }
  

  
    @kotlinx.serialization.Serializable
  public data class Data(
  
    val product: Product?
  ) {
    
      
        @kotlinx.serialization.Serializable
  public data class Product(
  
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
    val name: String,
    val description: String?
  ) {
    
    
  }
      
    
    
  }
      
    
    
  }
  

  public companion object {
    public val operationName: String = "GetProductById"

    public val dataDeserializer: kotlinx.serialization.DeserializationStrategy<Data> =
      kotlinx.serialization.serializer()

    public val variablesSerializer: kotlinx.serialization.SerializationStrategy<Variables> =
      kotlinx.serialization.serializer()
  }
}

public fun GetProductByIdQuery.ref(
  
    id: java.util.UUID,
  
  
): com.google.firebase.dataconnect.QueryRef<
    GetProductByIdQuery.Data,
    GetProductByIdQuery.Variables
  > =
  ref(
    
      GetProductByIdQuery.Variables(
        id=id,
  
      )
    
  )

public suspend fun GetProductByIdQuery.execute(
  
    id: java.util.UUID,
  
  
  ): com.google.firebase.dataconnect.QueryResult<
    GetProductByIdQuery.Data,
    GetProductByIdQuery.Variables
  > =
  ref(
    
      id=id,
  
    
  ).execute()


  public fun GetProductByIdQuery.flow(
    
      id: java.util.UUID,
  
    
    ): kotlinx.coroutines.flow.Flow<GetProductByIdQuery.Data> =
    ref(
        
          id=id,
  
        
      ).subscribe()
      .flow
      ._flow_map { querySubscriptionResult -> querySubscriptionResult.result.getOrNull() }
      ._flow_filterNotNull()
      ._flow_map { it.data }

