
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


public interface CountRequestsByStatusQuery :
    com.google.firebase.dataconnect.generated.GeneratedQuery<
      SasConnectorConnector,
      CountRequestsByStatusQuery.Data,
      CountRequestsByStatusQuery.Variables
    >
{
  
    @kotlinx.serialization.Serializable
  public data class Variables(
  
    val statusCode: String
  ) {
    
    
  }
  

  
    @kotlinx.serialization.Serializable
  public data class Data(
  
    val requests: List<RequestsItem>
  ) {
    
      
        @kotlinx.serialization.Serializable
  public data class RequestsItem(
  
    val id: @kotlinx.serialization.Serializable(with = com.google.firebase.dataconnect.serializers.UUIDSerializer::class) java.util.UUID
  ) {
    
    
  }
      
    
    
  }
  

  public companion object {
    public val operationName: String = "CountRequestsByStatus"

    public val dataDeserializer: kotlinx.serialization.DeserializationStrategy<Data> =
      kotlinx.serialization.serializer()

    public val variablesSerializer: kotlinx.serialization.SerializationStrategy<Variables> =
      kotlinx.serialization.serializer()
  }
}

public fun CountRequestsByStatusQuery.ref(
  
    statusCode: String,
  
  
): com.google.firebase.dataconnect.QueryRef<
    CountRequestsByStatusQuery.Data,
    CountRequestsByStatusQuery.Variables
  > =
  ref(
    
      CountRequestsByStatusQuery.Variables(
        statusCode=statusCode,
  
      )
    
  )

public suspend fun CountRequestsByStatusQuery.execute(
  
    statusCode: String,
  
  
  ): com.google.firebase.dataconnect.QueryResult<
    CountRequestsByStatusQuery.Data,
    CountRequestsByStatusQuery.Variables
  > =
  ref(
    
      statusCode=statusCode,
  
    
  ).execute()


  public fun CountRequestsByStatusQuery.flow(
    
      statusCode: String,
  
    
    ): kotlinx.coroutines.flow.Flow<CountRequestsByStatusQuery.Data> =
    ref(
        
          statusCode=statusCode,
  
        
      ).subscribe()
      .flow
      ._flow_map { querySubscriptionResult -> querySubscriptionResult.result.getOrNull() }
      ._flow_filterNotNull()
      ._flow_map { it.data }

