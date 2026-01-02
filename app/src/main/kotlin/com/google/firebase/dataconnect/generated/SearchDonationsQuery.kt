
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


public interface SearchDonationsQuery :
    com.google.firebase.dataconnect.generated.GeneratedQuery<
      SasConnectorConnector,
      SearchDonationsQuery.Data,
      SearchDonationsQuery.Variables
    >
{
  
    @kotlinx.serialization.Serializable
  public data class Variables(
  
    val donorName: com.google.firebase.dataconnect.OptionalVariable<String?>,
    val limit: com.google.firebase.dataconnect.OptionalVariable<Int?>,
    val offset: com.google.firebase.dataconnect.OptionalVariable<Int?>
  ) {
    
    
      
      @kotlin.DslMarker public annotation class BuilderDsl

      @BuilderDsl
      public interface Builder {
        public var donorName: String?
        public var limit: Int?
        public var offset: Int?
        
      }

      public companion object {
        @Suppress("NAME_SHADOWING")
        public fun build(
          
          block_: Builder.() -> Unit
        ): Variables {
          var donorName: com.google.firebase.dataconnect.OptionalVariable<String?> =
                com.google.firebase.dataconnect.OptionalVariable.Undefined
            var limit: com.google.firebase.dataconnect.OptionalVariable<Int?> =
                com.google.firebase.dataconnect.OptionalVariable.Undefined
            var offset: com.google.firebase.dataconnect.OptionalVariable<Int?> =
                com.google.firebase.dataconnect.OptionalVariable.Undefined
            

          return object : Builder {
            override var donorName: String?
              get() = throw UnsupportedOperationException("getting builder values is not supported")
              set(value_) { donorName = com.google.firebase.dataconnect.OptionalVariable.Value(value_) }
              
            override var limit: Int?
              get() = throw UnsupportedOperationException("getting builder values is not supported")
              set(value_) { limit = com.google.firebase.dataconnect.OptionalVariable.Value(value_) }
              
            override var offset: Int?
              get() = throw UnsupportedOperationException("getting builder values is not supported")
              set(value_) { offset = com.google.firebase.dataconnect.OptionalVariable.Value(value_) }
              
            
          }.apply(block_)
          .let {
            Variables(
              donorName=donorName,limit=limit,offset=offset,
            )
          }
        }
      }
    
  }
  

  
    @kotlinx.serialization.Serializable
  public data class Data(
  
    val donations: List<DonationsItem>
  ) {
    
      
        @kotlinx.serialization.Serializable
  public data class DonationsItem(
  
    val id: @kotlinx.serialization.Serializable(with = com.google.firebase.dataconnect.serializers.UUIDSerializer::class) java.util.UUID,
    val donationDate: com.google.firebase.dataconnect.LocalDate,
    val donorName: String,
    val estimatedValue: Double,
    val donorContact: String?,
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
    public val operationName: String = "SearchDonations"

    public val dataDeserializer: kotlinx.serialization.DeserializationStrategy<Data> =
      kotlinx.serialization.serializer()

    public val variablesSerializer: kotlinx.serialization.SerializationStrategy<Variables> =
      kotlinx.serialization.serializer()
  }
}

public fun SearchDonationsQuery.ref(
  
    
  
    block_: SearchDonationsQuery.Variables.Builder.() -> Unit = {}
  
): com.google.firebase.dataconnect.QueryRef<
    SearchDonationsQuery.Data,
    SearchDonationsQuery.Variables
  > =
  ref(
    
      SearchDonationsQuery.Variables.build(
        
  
    block_
      )
    
  )

public suspend fun SearchDonationsQuery.execute(
  
    
  
    block_: SearchDonationsQuery.Variables.Builder.() -> Unit = {}
  
  ): com.google.firebase.dataconnect.QueryResult<
    SearchDonationsQuery.Data,
    SearchDonationsQuery.Variables
  > =
  ref(
    
      
  
    block_
    
  ).execute()


  public fun SearchDonationsQuery.flow(
    
      
  
    block_: SearchDonationsQuery.Variables.Builder.() -> Unit = {}
    
    ): kotlinx.coroutines.flow.Flow<SearchDonationsQuery.Data> =
    ref(
        
          
  
    block_
        
      ).subscribe()
      .flow
      ._flow_map { querySubscriptionResult -> querySubscriptionResult.result.getOrNull() }
      ._flow_filterNotNull()
      ._flow_map { it.data }

