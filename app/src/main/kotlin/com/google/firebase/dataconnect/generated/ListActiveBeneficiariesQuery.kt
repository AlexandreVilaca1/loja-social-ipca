
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


public interface ListActiveBeneficiariesQuery :
    com.google.firebase.dataconnect.generated.GeneratedQuery<
      SasConnectorConnector,
      ListActiveBeneficiariesQuery.Data,
      ListActiveBeneficiariesQuery.Variables
    >
{
  
    @kotlinx.serialization.Serializable
  public data class Variables(
  
    val limit: com.google.firebase.dataconnect.OptionalVariable<Int?>,
    val offset: com.google.firebase.dataconnect.OptionalVariable<Int?>
  ) {
    
    
      
      @kotlin.DslMarker public annotation class BuilderDsl

      @BuilderDsl
      public interface Builder {
        public var limit: Int?
        public var offset: Int?
        
      }

      public companion object {
        @Suppress("NAME_SHADOWING")
        public fun build(
          
          block_: Builder.() -> Unit
        ): Variables {
          var limit: com.google.firebase.dataconnect.OptionalVariable<Int?> =
                com.google.firebase.dataconnect.OptionalVariable.Undefined
            var offset: com.google.firebase.dataconnect.OptionalVariable<Int?> =
                com.google.firebase.dataconnect.OptionalVariable.Undefined
            

          return object : Builder {
            override var limit: Int?
              get() = throw UnsupportedOperationException("getting builder values is not supported")
              set(value_) { limit = com.google.firebase.dataconnect.OptionalVariable.Value(value_) }
              
            override var offset: Int?
              get() = throw UnsupportedOperationException("getting builder values is not supported")
              set(value_) { offset = com.google.firebase.dataconnect.OptionalVariable.Value(value_) }
              
            
          }.apply(block_)
          .let {
            Variables(
              limit=limit,offset=offset,
            )
          }
        }
      }
    
  }
  

  
    @kotlinx.serialization.Serializable
  public data class Data(
  
    val beneficiaries: List<BeneficiariesItem>
  ) {
    
      
        @kotlinx.serialization.Serializable
  public data class BeneficiariesItem(
  
    val id: @kotlinx.serialization.Serializable(with = com.google.firebase.dataconnect.serializers.UUIDSerializer::class) java.util.UUID,
    val fullName: String,
    val studentNumer: Int,
    val nif: String,
    val course: String,
    val contactNumber: String,
    val address: String?,
    val createdAt: @kotlinx.serialization.Serializable(with = com.google.firebase.dataconnect.serializers.TimestampSerializer::class) com.google.firebase.Timestamp?,
    val updatedAt: @kotlinx.serialization.Serializable(with = com.google.firebase.dataconnect.serializers.TimestampSerializer::class) com.google.firebase.Timestamp?
  ) {
    
    
  }
      
    
    
  }
  

  public companion object {
    public val operationName: String = "ListActiveBeneficiaries"

    public val dataDeserializer: kotlinx.serialization.DeserializationStrategy<Data> =
      kotlinx.serialization.serializer()

    public val variablesSerializer: kotlinx.serialization.SerializationStrategy<Variables> =
      kotlinx.serialization.serializer()
  }
}

public fun ListActiveBeneficiariesQuery.ref(
  
    
  
    block_: ListActiveBeneficiariesQuery.Variables.Builder.() -> Unit = {}
  
): com.google.firebase.dataconnect.QueryRef<
    ListActiveBeneficiariesQuery.Data,
    ListActiveBeneficiariesQuery.Variables
  > =
  ref(
    
      ListActiveBeneficiariesQuery.Variables.build(
        
  
    block_
      )
    
  )

public suspend fun ListActiveBeneficiariesQuery.execute(
  
    
  
    block_: ListActiveBeneficiariesQuery.Variables.Builder.() -> Unit = {}
  
  ): com.google.firebase.dataconnect.QueryResult<
    ListActiveBeneficiariesQuery.Data,
    ListActiveBeneficiariesQuery.Variables
  > =
  ref(
    
      
  
    block_
    
  ).execute()


  public fun ListActiveBeneficiariesQuery.flow(
    
      
  
    block_: ListActiveBeneficiariesQuery.Variables.Builder.() -> Unit = {}
    
    ): kotlinx.coroutines.flow.Flow<ListActiveBeneficiariesQuery.Data> =
    ref(
        
          
  
    block_
        
      ).subscribe()
      .flow
      ._flow_map { querySubscriptionResult -> querySubscriptionResult.result.getOrNull() }
      ._flow_filterNotNull()
      ._flow_map { it.data }

