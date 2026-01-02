
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


public interface ListDonationItemsByDonationQuery :
    com.google.firebase.dataconnect.generated.GeneratedQuery<
      SasConnectorConnector,
      ListDonationItemsByDonationQuery.Data,
      ListDonationItemsByDonationQuery.Variables
    >
{
  
    @kotlinx.serialization.Serializable
  public data class Variables(
  
    val donationId: @kotlinx.serialization.Serializable(with = com.google.firebase.dataconnect.serializers.UUIDSerializer::class) java.util.UUID,
    val limit: com.google.firebase.dataconnect.OptionalVariable<Int?>,
    val offset: com.google.firebase.dataconnect.OptionalVariable<Int?>
  ) {
    
    
      
      @kotlin.DslMarker public annotation class BuilderDsl

      @BuilderDsl
      public interface Builder {
        public var donationId: java.util.UUID
        public var limit: Int?
        public var offset: Int?
        
      }

      public companion object {
        @Suppress("NAME_SHADOWING")
        public fun build(
          donationId: java.util.UUID,
          block_: Builder.() -> Unit
        ): Variables {
          var donationId= donationId
            var limit: com.google.firebase.dataconnect.OptionalVariable<Int?> =
                com.google.firebase.dataconnect.OptionalVariable.Undefined
            var offset: com.google.firebase.dataconnect.OptionalVariable<Int?> =
                com.google.firebase.dataconnect.OptionalVariable.Undefined
            

          return object : Builder {
            override var donationId: java.util.UUID
              get() = throw UnsupportedOperationException("getting builder values is not supported")
              set(value_) { donationId = value_ }
              
            override var limit: Int?
              get() = throw UnsupportedOperationException("getting builder values is not supported")
              set(value_) { limit = com.google.firebase.dataconnect.OptionalVariable.Value(value_) }
              
            override var offset: Int?
              get() = throw UnsupportedOperationException("getting builder values is not supported")
              set(value_) { offset = com.google.firebase.dataconnect.OptionalVariable.Value(value_) }
              
            
          }.apply(block_)
          .let {
            Variables(
              donationId=donationId,limit=limit,offset=offset,
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
    val donorName: String
  ) {
    
    
  }
      
        @kotlinx.serialization.Serializable
  public data class Lot(
  
    val id: @kotlinx.serialization.Serializable(with = com.google.firebase.dataconnect.serializers.UUIDSerializer::class) java.util.UUID,
    val lotCode: String,
    val product: Product?
  ) {
    
      
        @kotlinx.serialization.Serializable
  public data class Product(
  
    val id: @kotlinx.serialization.Serializable(with = com.google.firebase.dataconnect.serializers.UUIDSerializer::class) java.util.UUID,
    val name: String,
    val unitOfMeasure: String
  ) {
    
    
  }
      
    
    
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
    public val operationName: String = "ListDonationItemsByDonation"

    public val dataDeserializer: kotlinx.serialization.DeserializationStrategy<Data> =
      kotlinx.serialization.serializer()

    public val variablesSerializer: kotlinx.serialization.SerializationStrategy<Variables> =
      kotlinx.serialization.serializer()
  }
}

public fun ListDonationItemsByDonationQuery.ref(
  
    donationId: java.util.UUID,
  
    block_: ListDonationItemsByDonationQuery.Variables.Builder.() -> Unit = {}
  
): com.google.firebase.dataconnect.QueryRef<
    ListDonationItemsByDonationQuery.Data,
    ListDonationItemsByDonationQuery.Variables
  > =
  ref(
    
      ListDonationItemsByDonationQuery.Variables.build(
        donationId=donationId,
  
    block_
      )
    
  )

public suspend fun ListDonationItemsByDonationQuery.execute(
  
    donationId: java.util.UUID,
  
    block_: ListDonationItemsByDonationQuery.Variables.Builder.() -> Unit = {}
  
  ): com.google.firebase.dataconnect.QueryResult<
    ListDonationItemsByDonationQuery.Data,
    ListDonationItemsByDonationQuery.Variables
  > =
  ref(
    
      donationId=donationId,
  
    block_
    
  ).execute()


  public fun ListDonationItemsByDonationQuery.flow(
    
      donationId: java.util.UUID,
  
    block_: ListDonationItemsByDonationQuery.Variables.Builder.() -> Unit = {}
    
    ): kotlinx.coroutines.flow.Flow<ListDonationItemsByDonationQuery.Data> =
    ref(
        
          donationId=donationId,
  
    block_
        
      ).subscribe()
      .flow
      ._flow_map { querySubscriptionResult -> querySubscriptionResult.result.getOrNull() }
      ._flow_filterNotNull()
      ._flow_map { it.data }

