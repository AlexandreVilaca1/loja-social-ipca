
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


public interface ListAppointmentsByDateRangeQuery :
    com.google.firebase.dataconnect.generated.GeneratedQuery<
      SasConnectorConnector,
      ListAppointmentsByDateRangeQuery.Data,
      ListAppointmentsByDateRangeQuery.Variables
    >
{
  
    @kotlinx.serialization.Serializable
  public data class Variables(
  
    val startDate: com.google.firebase.dataconnect.LocalDate,
    val endDate: com.google.firebase.dataconnect.LocalDate,
    val limit: com.google.firebase.dataconnect.OptionalVariable<Int?>,
    val offset: com.google.firebase.dataconnect.OptionalVariable<Int?>
  ) {
    
    
      
      @kotlin.DslMarker public annotation class BuilderDsl

      @BuilderDsl
      public interface Builder {
        public var startDate: com.google.firebase.dataconnect.LocalDate
        public var endDate: com.google.firebase.dataconnect.LocalDate
        public var limit: Int?
        public var offset: Int?
        
      }

      public companion object {
        @Suppress("NAME_SHADOWING")
        public fun build(
          startDate: com.google.firebase.dataconnect.LocalDate,endDate: com.google.firebase.dataconnect.LocalDate,
          block_: Builder.() -> Unit
        ): Variables {
          var startDate= startDate
            var endDate= endDate
            var limit: com.google.firebase.dataconnect.OptionalVariable<Int?> =
                com.google.firebase.dataconnect.OptionalVariable.Undefined
            var offset: com.google.firebase.dataconnect.OptionalVariable<Int?> =
                com.google.firebase.dataconnect.OptionalVariable.Undefined
            

          return object : Builder {
            override var startDate: com.google.firebase.dataconnect.LocalDate
              get() = throw UnsupportedOperationException("getting builder values is not supported")
              set(value_) { startDate = value_ }
              
            override var endDate: com.google.firebase.dataconnect.LocalDate
              get() = throw UnsupportedOperationException("getting builder values is not supported")
              set(value_) { endDate = value_ }
              
            override var limit: Int?
              get() = throw UnsupportedOperationException("getting builder values is not supported")
              set(value_) { limit = com.google.firebase.dataconnect.OptionalVariable.Value(value_) }
              
            override var offset: Int?
              get() = throw UnsupportedOperationException("getting builder values is not supported")
              set(value_) { offset = com.google.firebase.dataconnect.OptionalVariable.Value(value_) }
              
            
          }.apply(block_)
          .let {
            Variables(
              startDate=startDate,endDate=endDate,limit=limit,offset=offset,
            )
          }
        }
      }
    
  }
  

  
    @kotlinx.serialization.Serializable
  public data class Data(
  
    val appointments: List<AppointmentsItem>
  ) {
    
      
        @kotlinx.serialization.Serializable
  public data class AppointmentsItem(
  
    val id: @kotlinx.serialization.Serializable(with = com.google.firebase.dataconnect.serializers.UUIDSerializer::class) java.util.UUID,
    val appointmentDate: com.google.firebase.dataconnect.LocalDate,
    val appointmentTime: String,
    val type: String,
    val observations: String?,
    val status: Status?,
    val createdAt: @kotlinx.serialization.Serializable(with = com.google.firebase.dataconnect.serializers.TimestampSerializer::class) com.google.firebase.Timestamp?,
    val updatedAt: @kotlinx.serialization.Serializable(with = com.google.firebase.dataconnect.serializers.TimestampSerializer::class) com.google.firebase.Timestamp?
  ) {
    
      
        @kotlinx.serialization.Serializable
  public data class Status(
  
    val id: @kotlinx.serialization.Serializable(with = com.google.firebase.dataconnect.serializers.UUIDSerializer::class) java.util.UUID,
    val code: String,
    val description: String
  ) {
    
    
  }
      
    
    
  }
      
    
    
  }
  

  public companion object {
    public val operationName: String = "ListAppointmentsByDateRange"

    public val dataDeserializer: kotlinx.serialization.DeserializationStrategy<Data> =
      kotlinx.serialization.serializer()

    public val variablesSerializer: kotlinx.serialization.SerializationStrategy<Variables> =
      kotlinx.serialization.serializer()
  }
}

public fun ListAppointmentsByDateRangeQuery.ref(
  
    startDate: com.google.firebase.dataconnect.LocalDate,endDate: com.google.firebase.dataconnect.LocalDate,
  
    block_: ListAppointmentsByDateRangeQuery.Variables.Builder.() -> Unit = {}
  
): com.google.firebase.dataconnect.QueryRef<
    ListAppointmentsByDateRangeQuery.Data,
    ListAppointmentsByDateRangeQuery.Variables
  > =
  ref(
    
      ListAppointmentsByDateRangeQuery.Variables.build(
        startDate=startDate,endDate=endDate,
  
    block_
      )
    
  )

public suspend fun ListAppointmentsByDateRangeQuery.execute(
  
    startDate: com.google.firebase.dataconnect.LocalDate,endDate: com.google.firebase.dataconnect.LocalDate,
  
    block_: ListAppointmentsByDateRangeQuery.Variables.Builder.() -> Unit = {}
  
  ): com.google.firebase.dataconnect.QueryResult<
    ListAppointmentsByDateRangeQuery.Data,
    ListAppointmentsByDateRangeQuery.Variables
  > =
  ref(
    
      startDate=startDate,endDate=endDate,
  
    block_
    
  ).execute()


  public fun ListAppointmentsByDateRangeQuery.flow(
    
      startDate: com.google.firebase.dataconnect.LocalDate,endDate: com.google.firebase.dataconnect.LocalDate,
  
    block_: ListAppointmentsByDateRangeQuery.Variables.Builder.() -> Unit = {}
    
    ): kotlinx.coroutines.flow.Flow<ListAppointmentsByDateRangeQuery.Data> =
    ref(
        
          startDate=startDate,endDate=endDate,
  
    block_
        
      ).subscribe()
      .flow
      ._flow_map { querySubscriptionResult -> querySubscriptionResult.result.getOrNull() }
      ._flow_filterNotNull()
      ._flow_map { it.data }

