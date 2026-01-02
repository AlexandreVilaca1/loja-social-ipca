
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



public interface CreateDistributionMutation :
    com.google.firebase.dataconnect.generated.GeneratedMutation<
      SasConnectorConnector,
      CreateDistributionMutation.Data,
      CreateDistributionMutation.Variables
    >
{
  
    @kotlinx.serialization.Serializable
  public data class Variables(
  
    val distributionDate: com.google.firebase.dataconnect.LocalDate,
    val observations: com.google.firebase.dataconnect.OptionalVariable<String?>,
    val responsibleStaffId: com.google.firebase.dataconnect.OptionalVariable<UserKey?>,
    val beneficiaryId: com.google.firebase.dataconnect.OptionalVariable<BeneficiaryKey?>,
    val statusId: com.google.firebase.dataconnect.OptionalVariable<StatusTypeKey?>
  ) {
    
    
      
      @kotlin.DslMarker public annotation class BuilderDsl

      @BuilderDsl
      public interface Builder {
        public var distributionDate: com.google.firebase.dataconnect.LocalDate
        public var observations: String?
        public var responsibleStaffId: UserKey?
        public var beneficiaryId: BeneficiaryKey?
        public var statusId: StatusTypeKey?
        
      }

      public companion object {
        @Suppress("NAME_SHADOWING")
        public fun build(
          distributionDate: com.google.firebase.dataconnect.LocalDate,
          block_: Builder.() -> Unit
        ): Variables {
          var distributionDate= distributionDate
            var observations: com.google.firebase.dataconnect.OptionalVariable<String?> =
                com.google.firebase.dataconnect.OptionalVariable.Undefined
            var responsibleStaffId: com.google.firebase.dataconnect.OptionalVariable<UserKey?> =
                com.google.firebase.dataconnect.OptionalVariable.Undefined
            var beneficiaryId: com.google.firebase.dataconnect.OptionalVariable<BeneficiaryKey?> =
                com.google.firebase.dataconnect.OptionalVariable.Undefined
            var statusId: com.google.firebase.dataconnect.OptionalVariable<StatusTypeKey?> =
                com.google.firebase.dataconnect.OptionalVariable.Undefined
            

          return object : Builder {
            override var distributionDate: com.google.firebase.dataconnect.LocalDate
              get() = throw UnsupportedOperationException("getting builder values is not supported")
              set(value_) { distributionDate = value_ }
              
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
              distributionDate=distributionDate,observations=observations,responsibleStaffId=responsibleStaffId,beneficiaryId=beneficiaryId,statusId=statusId,
            )
          }
        }
      }
    
  }
  

  
    @kotlinx.serialization.Serializable
  public data class Data(
  
    val distribution_insert: DistributionKey
  ) {
    
    
  }
  

  public companion object {
    public val operationName: String = "CreateDistribution"

    public val dataDeserializer: kotlinx.serialization.DeserializationStrategy<Data> =
      kotlinx.serialization.serializer()

    public val variablesSerializer: kotlinx.serialization.SerializationStrategy<Variables> =
      kotlinx.serialization.serializer()
  }
}

public fun CreateDistributionMutation.ref(
  
    distributionDate: com.google.firebase.dataconnect.LocalDate,
  
    block_: CreateDistributionMutation.Variables.Builder.() -> Unit = {}
  
): com.google.firebase.dataconnect.MutationRef<
    CreateDistributionMutation.Data,
    CreateDistributionMutation.Variables
  > =
  ref(
    
      CreateDistributionMutation.Variables.build(
        distributionDate=distributionDate,
  
    block_
      )
    
  )

public suspend fun CreateDistributionMutation.execute(
  
    distributionDate: com.google.firebase.dataconnect.LocalDate,
  
    block_: CreateDistributionMutation.Variables.Builder.() -> Unit = {}
  
  ): com.google.firebase.dataconnect.MutationResult<
    CreateDistributionMutation.Data,
    CreateDistributionMutation.Variables
  > =
  ref(
    
      distributionDate=distributionDate,
  
    block_
    
  ).execute()


