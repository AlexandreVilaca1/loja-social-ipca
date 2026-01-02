
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



public interface DeleteBeneficiaryMutation :
    com.google.firebase.dataconnect.generated.GeneratedMutation<
      SasConnectorConnector,
      DeleteBeneficiaryMutation.Data,
      DeleteBeneficiaryMutation.Variables
    >
{
  
    @kotlinx.serialization.Serializable
  public data class Variables(
  
    val id: @kotlinx.serialization.Serializable(with = com.google.firebase.dataconnect.serializers.UUIDSerializer::class) java.util.UUID
  ) {
    
    
  }
  

  
    @kotlinx.serialization.Serializable
  public data class Data(
  
    val beneficiary_delete: BeneficiaryKey?
  ) {
    
    
  }
  

  public companion object {
    public val operationName: String = "DeleteBeneficiary"

    public val dataDeserializer: kotlinx.serialization.DeserializationStrategy<Data> =
      kotlinx.serialization.serializer()

    public val variablesSerializer: kotlinx.serialization.SerializationStrategy<Variables> =
      kotlinx.serialization.serializer()
  }
}

public fun DeleteBeneficiaryMutation.ref(
  
    id: java.util.UUID,
  
  
): com.google.firebase.dataconnect.MutationRef<
    DeleteBeneficiaryMutation.Data,
    DeleteBeneficiaryMutation.Variables
  > =
  ref(
    
      DeleteBeneficiaryMutation.Variables(
        id=id,
  
      )
    
  )

public suspend fun DeleteBeneficiaryMutation.execute(
  
    id: java.util.UUID,
  
  
  ): com.google.firebase.dataconnect.MutationResult<
    DeleteBeneficiaryMutation.Data,
    DeleteBeneficiaryMutation.Variables
  > =
  ref(
    
      id=id,
  
    
  ).execute()


