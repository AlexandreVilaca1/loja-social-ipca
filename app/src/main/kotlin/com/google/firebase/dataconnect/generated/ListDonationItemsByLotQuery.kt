
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


public interface ListDonationItemsByLotQuery :
    com.google.firebase.dataconnect.generated.GeneratedQuery<
      SasConnectorConnector,
      ListDonationItemsByLotQuery.Data,
      ListDonationItemsByLotQuery.Variables
    >
{
  
    @kotlinx.serialization.Serializable
  public data class Variables(
  
    val lotId: @kotlinx.serialization.Serializable(with = com.google.firebase.dataconnect.serializers.UUIDSerializer::class) java.util.UUID,
    val limit: com.google.firebase.dataconnect.OptionalVariable<Int?>,
    val offset: com.google.firebase.dataconnect.OptionalVariable<Int?>
  ) {
    
    
      
      @kotlin.DslMarker public annotation class BuilderDsl

      @BuilderDsl
      public interface Builder {
        public var lotId: java.util.UUID
        public var limit: Int?
        public var offset: Int?
        
      }

      public companion object {
        @Suppress("NAME_SHADOWING")
        public fun build(
          lotId: java.util.UUID,
          block_: Builder.() -> Unit
        ): Variables {
          var lotId= lotId
            var limit: com.google.firebase.dataconnect.OptionalVariable<Int?> =
                com.google.firebase.dataconnect.OptionalVariable.Undefined
            var offset: com.google.firebase.dataconnect.OptionalVariable<Int?> =
                com.google.firebase.dataconnect.OptionalVariable.Undefined
            

          return object : Builder {
            override var lotId: java.util.UUID
              get() = throw UnsupportedOperationException("getting builder values is not supported")
              set(value_) { lotId = value_ }
              
            override var limit: Int?
              get() = throw UnsupportedOperationException("getting builder values is not supported")
              set(value_) { limit = com.google.firebase.dataconnect.OptionalVariable.Value(value_) }
              
            override var offset: Int?
              get() = throw UnsupportedOperationException("getting builder values is not supported")
              set(value_) { offset = com.google.firebase.dataconnect.OptionalVariable.Value(value_) }
              
            
          }.apply(block_)
          .let {
            Variables(
              lotId=lotId,limit=limit,offset=offset,
            )
          }
        }
      }
    
  }
  

  
    @kotlinx.serialization.Serializable
  public data class Data(
  
    val donationItems: List<DonationItemsItem>
  ) {
    
      
        @kotlinx.serialization.Serializable
  public data class DonationItemsItem(
  
    val donation: Donation,
    val lot: Lot,
    val quantity: Int,
    val state: State?,
    val observations: String?,
    val createdAt: @kotlinx.serialization.Serializable(with = com.google.firebase.dataconnect.serializers.TimestampSerializer::class) com.google.firebase.Timestamp?
  ) {
    
      
        @kotlinx.serialization.Serializable
  public data class Donation(
  
    val id: @kotlinx.serialization.Serializable(with = com.google.firebase.dataconnect.serializers.UUIDSerializer::class) java.util.UUID,
    val donorName: String,
    val donationDate: com.google.firebase.dataconnect.LocalDate
  ) {
    
    
  }
      
        @kotlinx.serialization.Serializable
  public data class Lot(
  
    val id: @kotlinx.serialization.Serializable(with = com.google.firebase.dataconnect.serializers.UUIDSerializer::class) java.util.UUID,
    val lotCode: String
  ) {
    
    
  }
      
        @kotlinx.serialization.Serializable
  public data class State(
  
    val id: @kotlinx.serialization.Serializable(with = com.google.firebase.dataconnect.serializers.UUIDSerializer::class) java.util.UUID,
    val code: String,
    val description: String
  ) {
    
    
  }
      
    
    
  }
      
    
    
  }
  

  public companion object {
    public val operationName: String = "ListDonationItemsByLot"

    public val dataDeserializer: kotlinx.serialization.DeserializationStrategy<Data> =
      kotlinx.serialization.serializer()

    public val variablesSerializer: kotlinx.serialization.SerializationStrategy<Variables> =
      kotlinx.serialization.serializer()
  }
}

public fun ListDonationItemsByLotQuery.ref(
  
    lotId: java.util.UUID,
  
    block_: ListDonationItemsByLotQuery.Variables.Builder.() -> Unit = {}
  
): com.google.firebase.dataconnect.QueryRef<
    ListDonationItemsByLotQuery.Data,
    ListDonationItemsByLotQuery.Variables
  > =
  ref(
    
      ListDonationItemsByLotQuery.Variables.build(
        lotId=lotId,
  
    block_
      )
    
  )

public suspend fun ListDonationItemsByLotQuery.execute(
  
    lotId: java.util.UUID,
  
    block_: ListDonationItemsByLotQuery.Variables.Builder.() -> Unit = {}
  
  ): com.google.firebase.dataconnect.QueryResult<
    ListDonationItemsByLotQuery.Data,
    ListDonationItemsByLotQuery.Variables
  > =
  ref(
    
      lotId=lotId,
  
    block_
    
  ).execute()


  public fun ListDonationItemsByLotQuery.flow(
    
      lotId: java.util.UUID,
  
    block_: ListDonationItemsByLotQuery.Variables.Builder.() -> Unit = {}
    
    ): kotlinx.coroutines.flow.Flow<ListDonationItemsByLotQuery.Data> =
    ref(
        
          lotId=lotId,
  
    block_
        
      ).subscribe()
      .flow
      ._flow_map { querySubscriptionResult -> querySubscriptionResult.result.getOrNull() }
      ._flow_filterNotNull()
      ._flow_map { it.data }

