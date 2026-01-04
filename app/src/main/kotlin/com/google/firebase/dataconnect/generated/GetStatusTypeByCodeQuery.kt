
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


public interface GetStatusTypeByCodeQuery :
    com.google.firebase.dataconnect.generated.GeneratedQuery<
      SasConnectorConnector,
      GetStatusTypeByCodeQuery.Data,
      GetStatusTypeByCodeQuery.Variables
    >
{
  
    @kotlinx.serialization.Serializable
  public data class Variables(
  
    val code: String
  ) {
    
    
  }
  

  
    @kotlinx.serialization.Serializable
  public data class Data(
  
    val statusTypes: List<StatusTypesItem>
  ) {
    
      
        @kotlinx.serialization.Serializable
  public data class StatusTypesItem(
  
    val id: @kotlinx.serialization.Serializable(with = com.google.firebase.dataconnect.serializers.UUIDSerializer::class) java.util.UUID,
    val code: String,
    val description: String,
    val color: String?
  ) {
    
    
  }
      
    
    
  }
  

  public companion object {
    public val operationName: String = "GetStatusTypeByCode"

    public val dataDeserializer: kotlinx.serialization.DeserializationStrategy<Data> =
      kotlinx.serialization.serializer()

    public val variablesSerializer: kotlinx.serialization.SerializationStrategy<Variables> =
      kotlinx.serialization.serializer()
  }
}

public fun GetStatusTypeByCodeQuery.ref(
  
    code: String,
  
  
): com.google.firebase.dataconnect.QueryRef<
    GetStatusTypeByCodeQuery.Data,
    GetStatusTypeByCodeQuery.Variables
  > =
  ref(
    
      GetStatusTypeByCodeQuery.Variables(
        code=code,
  
      )
    
  )

public suspend fun GetStatusTypeByCodeQuery.execute(
  
    code: String,
  
  
  ): com.google.firebase.dataconnect.QueryResult<
    GetStatusTypeByCodeQuery.Data,
    GetStatusTypeByCodeQuery.Variables
  > =
  ref(
    
      code=code,
  
    
  ).execute()


  public fun GetStatusTypeByCodeQuery.flow(
    
      code: String,
  
    
    ): kotlinx.coroutines.flow.Flow<GetStatusTypeByCodeQuery.Data> =
    ref(
        
          code=code,
  
        
      ).subscribe()
      .flow
      ._flow_map { querySubscriptionResult -> querySubscriptionResult.result.getOrNull() }
      ._flow_filterNotNull()
      ._flow_map { it.data }

