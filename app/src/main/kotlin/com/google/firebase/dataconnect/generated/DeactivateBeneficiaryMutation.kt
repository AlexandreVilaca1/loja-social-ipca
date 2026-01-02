
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



public interface DeactivateBeneficiaryMutation :
    com.google.firebase.dataconnect.generated.GeneratedMutation<
      SasConnectorConnector,
      DeactivateBeneficiaryMutation.Data,
      DeactivateBeneficiaryMutation.Variables
    >
{
  
    @kotlinx.serialization.Serializable
  public data class Variables(
  
    val id: @kotlinx.serialization.Serializable(with = com.google.firebase.dataconnect.serializers.UUIDSerializer::class) java.util.UUID
  ) {
    
    
  }
  

  
    @kotlinx.serialization.Serializable
  public data class Data(
  
    val beneficiary_update: BeneficiaryKey?
  ) {
    
    
  }
  

  public companion object {
    public val operationName: String = "DeactivateBeneficiary"

    public val dataDeserializer: kotlinx.serialization.DeserializationStrategy<Data> =
      kotlinx.serialization.serializer()

    public val variablesSerializer: kotlinx.serialization.SerializationStrategy<Variables> =
      kotlinx.serialization.serializer()
  }
}

public fun DeactivateBeneficiaryMutation.ref(
  
    id: java.util.UUID,
  
  
): com.google.firebase.dataconnect.MutationRef<
    DeactivateBeneficiaryMutation.Data,
    DeactivateBeneficiaryMutation.Variables
  > =
  ref(
    
      DeactivateBeneficiaryMutation.Variables(
        id=id,
  
      )
    
  )

public suspend fun DeactivateBeneficiaryMutation.execute(
  
    id: java.util.UUID,
  
  
  ): com.google.firebase.dataconnect.MutationResult<
    DeactivateBeneficiaryMutation.Data,
    DeactivateBeneficiaryMutation.Variables
  > =
  ref(
    
      id=id,
  
    
  ).execute()


