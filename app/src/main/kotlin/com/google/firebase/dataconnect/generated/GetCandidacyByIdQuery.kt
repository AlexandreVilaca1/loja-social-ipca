
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


public interface GetCandidacyByIdQuery :
    com.google.firebase.dataconnect.generated.GeneratedQuery<
      SasConnectorConnector,
      GetCandidacyByIdQuery.Data,
      GetCandidacyByIdQuery.Variables
    >
{
  
    @kotlinx.serialization.Serializable
  public data class Variables(
  
    val id: @kotlinx.serialization.Serializable(with = com.google.firebase.dataconnect.serializers.UUIDSerializer::class) java.util.UUID
  ) {
    
    
  }
  

  
    @kotlinx.serialization.Serializable
  public data class Data(
  
    val candidacy: Candidacy?
  ) {
    
      
        @kotlinx.serialization.Serializable
  public data class Candidacy(
  
    val id: @kotlinx.serialization.Serializable(with = com.google.firebase.dataconnect.serializers.UUIDSerializer::class) java.util.UUID,
    val beneficiary: Beneficiary,
    val applicationDate: com.google.firebase.dataconnect.LocalDate,
    val status: Status?,
    val observations: String?,
    val createdAt: @kotlinx.serialization.Serializable(with = com.google.firebase.dataconnect.serializers.TimestampSerializer::class) com.google.firebase.Timestamp,
    val updatedAt: @kotlinx.serialization.Serializable(with = com.google.firebase.dataconnect.serializers.TimestampSerializer::class) com.google.firebase.Timestamp
  ) {
    
      
        @kotlinx.serialization.Serializable
  public data class Beneficiary(
  
    val id: @kotlinx.serialization.Serializable(with = com.google.firebase.dataconnect.serializers.UUIDSerializer::class) java.util.UUID,
    val fullName: String,
    val studentNumer: Int,
    val nif: String,
    val course: String,
    val isActive: Boolean,
    val contactNumber: String,
    val address: String?,
    val observations: String?
  ) {
    
    
  }
      
        @kotlinx.serialization.Serializable
  public data class Status(
  
    val id: @kotlinx.serialization.Serializable(with = com.google.firebase.dataconnect.serializers.UUIDSerializer::class) java.util.UUID,
    val code: String,
    val description: String,
    val color: String?,
    val sortOrder: Int
  ) {
    
    
  }
      
    
    
  }
      
    
    
  }
  

  public companion object {
    public val operationName: String = "GetCandidacyById"

    public val dataDeserializer: kotlinx.serialization.DeserializationStrategy<Data> =
      kotlinx.serialization.serializer()

    public val variablesSerializer: kotlinx.serialization.SerializationStrategy<Variables> =
      kotlinx.serialization.serializer()
  }
}

public fun GetCandidacyByIdQuery.ref(
  
    id: java.util.UUID,
  
  
): com.google.firebase.dataconnect.QueryRef<
    GetCandidacyByIdQuery.Data,
    GetCandidacyByIdQuery.Variables
  > =
  ref(
    
      GetCandidacyByIdQuery.Variables(
        id=id,
  
      )
    
  )

public suspend fun GetCandidacyByIdQuery.execute(
  
    id: java.util.UUID,
  
  
  ): com.google.firebase.dataconnect.QueryResult<
    GetCandidacyByIdQuery.Data,
    GetCandidacyByIdQuery.Variables
  > =
  ref(
    
      id=id,
  
    
  ).execute()


  public fun GetCandidacyByIdQuery.flow(
    
      id: java.util.UUID,
  
    
    ): kotlinx.coroutines.flow.Flow<GetCandidacyByIdQuery.Data> =
    ref(
        
          id=id,
  
        
      ).subscribe()
      .flow
      ._flow_map { querySubscriptionResult -> querySubscriptionResult.result.getOrNull() }
      ._flow_filterNotNull()
      ._flow_map { it.data }

