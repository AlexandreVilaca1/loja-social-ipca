
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


public interface ListLogsByLogTypeCodeQuery :
    com.google.firebase.dataconnect.generated.GeneratedQuery<
      SasConnectorConnector,
      ListLogsByLogTypeCodeQuery.Data,
      ListLogsByLogTypeCodeQuery.Variables
    >
{
  
    @kotlinx.serialization.Serializable
  public data class Variables(
  
    val logTypeCode: String,
    val limit: com.google.firebase.dataconnect.OptionalVariable<Int?>,
    val offset: com.google.firebase.dataconnect.OptionalVariable<Int?>
  ) {
    
    
      
      @kotlin.DslMarker public annotation class BuilderDsl

      @BuilderDsl
      public interface Builder {
        public var logTypeCode: String
        public var limit: Int?
        public var offset: Int?
        
      }

      public companion object {
        @Suppress("NAME_SHADOWING")
        public fun build(
          logTypeCode: String,
          block_: Builder.() -> Unit
        ): Variables {
          var logTypeCode= logTypeCode
            var limit: com.google.firebase.dataconnect.OptionalVariable<Int?> =
                com.google.firebase.dataconnect.OptionalVariable.Undefined
            var offset: com.google.firebase.dataconnect.OptionalVariable<Int?> =
                com.google.firebase.dataconnect.OptionalVariable.Undefined
            

          return object : Builder {
            override var logTypeCode: String
              get() = throw UnsupportedOperationException("getting builder values is not supported")
              set(value_) { logTypeCode = value_ }
              
            override var limit: Int?
              get() = throw UnsupportedOperationException("getting builder values is not supported")
              set(value_) { limit = com.google.firebase.dataconnect.OptionalVariable.Value(value_) }
              
            override var offset: Int?
              get() = throw UnsupportedOperationException("getting builder values is not supported")
              set(value_) { offset = com.google.firebase.dataconnect.OptionalVariable.Value(value_) }
              
            
          }.apply(block_)
          .let {
            Variables(
              logTypeCode=logTypeCode,limit=limit,offset=offset,
            )
          }
        }
      }
    
  }
  

  
    @kotlinx.serialization.Serializable
  public data class Data(
  
    val logs: List<LogsItem>
  ) {
    
      
        @kotlinx.serialization.Serializable
  public data class LogsItem(
  
    val id: @kotlinx.serialization.Serializable(with = com.google.firebase.dataconnect.serializers.UUIDSerializer::class) java.util.UUID,
    val description: String,
    val logType: LogType,
    val user: User,
    val createdAt: @kotlinx.serialization.Serializable(with = com.google.firebase.dataconnect.serializers.TimestampSerializer::class) com.google.firebase.Timestamp
  ) {
    
      
        @kotlinx.serialization.Serializable
  public data class LogType(
  
    val id: @kotlinx.serialization.Serializable(with = com.google.firebase.dataconnect.serializers.UUIDSerializer::class) java.util.UUID,
    val name: String,
    val code: String?
  ) {
    
    
  }
      
        @kotlinx.serialization.Serializable
  public data class User(
  
    val id: @kotlinx.serialization.Serializable(with = com.google.firebase.dataconnect.serializers.UUIDSerializer::class) java.util.UUID,
    val name: String,
    val email: String
  ) {
    
    
  }
      
    
    
  }
      
    
    
  }
  

  public companion object {
    public val operationName: String = "ListLogsByLogTypeCode"

    public val dataDeserializer: kotlinx.serialization.DeserializationStrategy<Data> =
      kotlinx.serialization.serializer()

    public val variablesSerializer: kotlinx.serialization.SerializationStrategy<Variables> =
      kotlinx.serialization.serializer()
  }
}

public fun ListLogsByLogTypeCodeQuery.ref(
  
    logTypeCode: String,
  
    block_: ListLogsByLogTypeCodeQuery.Variables.Builder.() -> Unit = {}
  
): com.google.firebase.dataconnect.QueryRef<
    ListLogsByLogTypeCodeQuery.Data,
    ListLogsByLogTypeCodeQuery.Variables
  > =
  ref(
    
      ListLogsByLogTypeCodeQuery.Variables.build(
        logTypeCode=logTypeCode,
  
    block_
      )
    
  )

public suspend fun ListLogsByLogTypeCodeQuery.execute(
  
    logTypeCode: String,
  
    block_: ListLogsByLogTypeCodeQuery.Variables.Builder.() -> Unit = {}
  
  ): com.google.firebase.dataconnect.QueryResult<
    ListLogsByLogTypeCodeQuery.Data,
    ListLogsByLogTypeCodeQuery.Variables
  > =
  ref(
    
      logTypeCode=logTypeCode,
  
    block_
    
  ).execute()


  public fun ListLogsByLogTypeCodeQuery.flow(
    
      logTypeCode: String,
  
    block_: ListLogsByLogTypeCodeQuery.Variables.Builder.() -> Unit = {}
    
    ): kotlinx.coroutines.flow.Flow<ListLogsByLogTypeCodeQuery.Data> =
    ref(
        
          logTypeCode=logTypeCode,
  
    block_
        
      ).subscribe()
      .flow
      ._flow_map { querySubscriptionResult -> querySubscriptionResult.result.getOrNull() }
      ._flow_filterNotNull()
      ._flow_map { it.data }

