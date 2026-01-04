
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


public interface GetLogTypeByCodeQuery :
    com.google.firebase.dataconnect.generated.GeneratedQuery<
      SasConnectorConnector,
      GetLogTypeByCodeQuery.Data,
      GetLogTypeByCodeQuery.Variables
    >
{
  
    @kotlinx.serialization.Serializable
  public data class Variables(
  
    val code: String
  ) {
    
    
  }
  

  
    @kotlinx.serialization.Serializable
  public data class Data(
  
    val logTypes: List<LogTypesItem>
  ) {
    
      
        @kotlinx.serialization.Serializable
  public data class LogTypesItem(
  
    val id: @kotlinx.serialization.Serializable(with = com.google.firebase.dataconnect.serializers.UUIDSerializer::class) java.util.UUID,
    val name: String,
    val code: String?,
    val createdAt: @kotlinx.serialization.Serializable(with = com.google.firebase.dataconnect.serializers.TimestampSerializer::class) com.google.firebase.Timestamp
  ) {
    
    
  }
      
    
    
  }
  

  public companion object {
    public val operationName: String = "GetLogTypeByCode"

    public val dataDeserializer: kotlinx.serialization.DeserializationStrategy<Data> =
      kotlinx.serialization.serializer()

    public val variablesSerializer: kotlinx.serialization.SerializationStrategy<Variables> =
      kotlinx.serialization.serializer()
  }
}

public fun GetLogTypeByCodeQuery.ref(
  
    code: String,
  
  
): com.google.firebase.dataconnect.QueryRef<
    GetLogTypeByCodeQuery.Data,
    GetLogTypeByCodeQuery.Variables
  > =
  ref(
    
      GetLogTypeByCodeQuery.Variables(
        code=code,
  
      )
    
  )

public suspend fun GetLogTypeByCodeQuery.execute(
  
    code: String,
  
  
  ): com.google.firebase.dataconnect.QueryResult<
    GetLogTypeByCodeQuery.Data,
    GetLogTypeByCodeQuery.Variables
  > =
  ref(
    
      code=code,
  
    
  ).execute()


  public fun GetLogTypeByCodeQuery.flow(
    
      code: String,
  
    
    ): kotlinx.coroutines.flow.Flow<GetLogTypeByCodeQuery.Data> =
    ref(
        
          code=code,
  
        
      ).subscribe()
      .flow
      ._flow_map { querySubscriptionResult -> querySubscriptionResult.result.getOrNull() }
      ._flow_filterNotNull()
      ._flow_map { it.data }

