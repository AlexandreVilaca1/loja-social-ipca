
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


public interface CountCandidaciesByStatusQuery :
    com.google.firebase.dataconnect.generated.GeneratedQuery<
      SasConnectorConnector,
      CountCandidaciesByStatusQuery.Data,
      CountCandidaciesByStatusQuery.Variables
    >
{
  
    @kotlinx.serialization.Serializable
  public data class Variables(
  
    val statusCode: String
  ) {
    
    
  }
  

  
    @kotlinx.serialization.Serializable
  public data class Data(
  
    val candidacies: List<CandidaciesItem>
  ) {
    
      
        @kotlinx.serialization.Serializable
  public data class CandidaciesItem(
  
    val id: @kotlinx.serialization.Serializable(with = com.google.firebase.dataconnect.serializers.UUIDSerializer::class) java.util.UUID
  ) {
    
    
  }
      
    
    
  }
  

  public companion object {
    public val operationName: String = "CountCandidaciesByStatus"

    public val dataDeserializer: kotlinx.serialization.DeserializationStrategy<Data> =
      kotlinx.serialization.serializer()

    public val variablesSerializer: kotlinx.serialization.SerializationStrategy<Variables> =
      kotlinx.serialization.serializer()
  }
}

public fun CountCandidaciesByStatusQuery.ref(
  
    statusCode: String,
  
  
): com.google.firebase.dataconnect.QueryRef<
    CountCandidaciesByStatusQuery.Data,
    CountCandidaciesByStatusQuery.Variables
  > =
  ref(
    
      CountCandidaciesByStatusQuery.Variables(
        statusCode=statusCode,
  
      )
    
  )

public suspend fun CountCandidaciesByStatusQuery.execute(
  
    statusCode: String,
  
  
  ): com.google.firebase.dataconnect.QueryResult<
    CountCandidaciesByStatusQuery.Data,
    CountCandidaciesByStatusQuery.Variables
  > =
  ref(
    
      statusCode=statusCode,
  
    
  ).execute()


  public fun CountCandidaciesByStatusQuery.flow(
    
      statusCode: String,
  
    
    ): kotlinx.coroutines.flow.Flow<CountCandidaciesByStatusQuery.Data> =
    ref(
        
          statusCode=statusCode,
  
        
      ).subscribe()
      .flow
      ._flow_map { querySubscriptionResult -> querySubscriptionResult.result.getOrNull() }
      ._flow_filterNotNull()
      ._flow_map { it.data }

