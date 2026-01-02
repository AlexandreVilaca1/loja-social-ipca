
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



public interface UpdateDistributionMutation :
    com.google.firebase.dataconnect.generated.GeneratedMutation<
      SasConnectorConnector,
      UpdateDistributionMutation.Data,
      UpdateDistributionMutation.Variables
    >
{
  
    @kotlinx.serialization.Serializable
  public data class Variables(
  
    val id: @kotlinx.serialization.Serializable(with = com.google.firebase.dataconnect.serializers.UUIDSerializer::class) java.util.UUID,
    val distributionDate: com.google.firebase.dataconnect.OptionalVariable<com.google.firebase.dataconnect.LocalDate?>,
    val observations: com.google.firebase.dataconnect.OptionalVariable<String?>,
    val responsibleStaffId: com.google.firebase.dataconnect.OptionalVariable<UserKey?>,
    val beneficiaryId: com.google.firebase.dataconnect.OptionalVariable<BeneficiaryKey?>,
    val statusId: com.google.firebase.dataconnect.OptionalVariable<StatusTypeKey?>
  ) {
    
    
      
      @kotlin.DslMarker public annotation class BuilderDsl

      @BuilderDsl
      public interface Builder {
        public var id: java.util.UUID
        public var distributionDate: com.google.firebase.dataconnect.LocalDate?
        public var observations: String?
        public var responsibleStaffId: UserKey?
        public var beneficiaryId: BeneficiaryKey?
        public var statusId: StatusTypeKey?
        
      }

      public companion object {
        @Suppress("NAME_SHADOWING")
        public fun build(
          id: java.util.UUID,
          block_: Builder.() -> Unit
        ): Variables {
          var id= id
            var distributionDate: com.google.firebase.dataconnect.OptionalVariable<com.google.firebase.dataconnect.LocalDate?> =
                com.google.firebase.dataconnect.OptionalVariable.Undefined
            var observations: com.google.firebase.dataconnect.OptionalVariable<String?> =
                com.google.firebase.dataconnect.OptionalVariable.Undefined
            var responsibleStaffId: com.google.firebase.dataconnect.OptionalVariable<UserKey?> =
                com.google.firebase.dataconnect.OptionalVariable.Undefined
            var beneficiaryId: com.google.firebase.dataconnect.OptionalVariable<BeneficiaryKey?> =
                com.google.firebase.dataconnect.OptionalVariable.Undefined
            var statusId: com.google.firebase.dataconnect.OptionalVariable<StatusTypeKey?> =
                com.google.firebase.dataconnect.OptionalVariable.Undefined
            

          return object : Builder {
            override var id: java.util.UUID
              get() = throw UnsupportedOperationException("getting builder values is not supported")
              set(value_) { id = value_ }
              
            override var distributionDate: com.google.firebase.dataconnect.LocalDate?
              get() = throw UnsupportedOperationException("getting builder values is not supported")
              set(value_) { distributionDate = com.google.firebase.dataconnect.OptionalVariable.Value(value_) }
              
            override var observations: String?
              get() = throw UnsupportedOperationException("getting builder values is not supported")
              set(value_) { observations = com.google.firebase.dataconnect.OptionalVariable.Value(value_) }
              
            override var responsibleStaffId: UserKey?
              get() = throw UnsupportedOperationException("getting builder values is not supported")
              set(value_) { responsibleStaffId = com.google.firebase.dataconnect.OptionalVariable.Value(value_) }
              
            override var beneficiaryId: BeneficiaryKey?
              get() = throw UnsupportedOperationException("getting builder values is not supported")
              set(value_) { beneficiaryId = com.google.firebase.dataconnect.OptionalVariable.Value(value_) }
              
            override var statusId: StatusTypeKey?
              get() = throw UnsupportedOperationException("getting builder values is not supported")
              set(value_) { statusId = com.google.firebase.dataconnect.OptionalVariable.Value(value_) }
              
            
          }.apply(block_)
          .let {
            Variables(
              id=id,distributionDate=distributionDate,observations=observations,responsibleStaffId=responsibleStaffId,beneficiaryId=beneficiaryId,statusId=statusId,
            )
          }
        }
      }
    
  }
  

  
    @kotlinx.serialization.Serializable
  public data class Data(
  
    val distribution_update: DistributionKey?
  ) {
    
    
  }
  

  public companion object {
    public val operationName: String = "UpdateDistribution"

    public val dataDeserializer: kotlinx.serialization.DeserializationStrategy<Data> =
      kotlinx.serialization.serializer()

    public val variablesSerializer: kotlinx.serialization.SerializationStrategy<Variables> =
      kotlinx.serialization.serializer()
  }
}

public fun UpdateDistributionMutation.ref(
  
    id: java.util.UUID,
  
    block_: UpdateDistributionMutation.Variables.Builder.() -> Unit = {}
  
): com.google.firebase.dataconnect.MutationRef<
    UpdateDistributionMutation.Data,
    UpdateDistributionMutation.Variables
  > =
  ref(
    
      UpdateDistributionMutation.Variables.build(
        id=id,
  
    block_
      )
    
  )

public suspend fun UpdateDistributionMutation.execute(
  
    id: java.util.UUID,
  
    block_: UpdateDistributionMutation.Variables.Builder.() -> Unit = {}
  
  ): com.google.firebase.dataconnect.MutationResult<
    UpdateDistributionMutation.Data,
    UpdateDistributionMutation.Variables
  > =
  ref(
    
      id=id,
  
    block_
    
  ).execute()


