
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



public interface CreateDonationMutation :
    com.google.firebase.dataconnect.generated.GeneratedMutation<
      SasConnectorConnector,
      CreateDonationMutation.Data,
      CreateDonationMutation.Variables
    >
{
  
    @kotlinx.serialization.Serializable
  public data class Variables(
  
    val donationDate: com.google.firebase.dataconnect.LocalDate,
    val donorName: String,
    val estimatedValue: Double,
    val donorContact: com.google.firebase.dataconnect.OptionalVariable<String?>,
    val observations: com.google.firebase.dataconnect.OptionalVariable<String?>,
    val appointmentId: com.google.firebase.dataconnect.OptionalVariable<AppointmentKey?>,
    val statusId: com.google.firebase.dataconnect.OptionalVariable<StatusTypeKey?>
  ) {
    
    
      
      @kotlin.DslMarker public annotation class BuilderDsl

      @BuilderDsl
      public interface Builder {
        public var donationDate: com.google.firebase.dataconnect.LocalDate
        public var donorName: String
        public var estimatedValue: Double
        public var donorContact: String?
        public var observations: String?
        public var appointmentId: AppointmentKey?
        public var statusId: StatusTypeKey?
        
      }

      public companion object {
        @Suppress("NAME_SHADOWING")
        public fun build(
          donationDate: com.google.firebase.dataconnect.LocalDate,donorName: String,estimatedValue: Double,
          block_: Builder.() -> Unit
        ): Variables {
          var donationDate= donationDate
            var donorName= donorName
            var estimatedValue= estimatedValue
            var donorContact: com.google.firebase.dataconnect.OptionalVariable<String?> =
                com.google.firebase.dataconnect.OptionalVariable.Undefined
            var observations: com.google.firebase.dataconnect.OptionalVariable<String?> =
                com.google.firebase.dataconnect.OptionalVariable.Undefined
            var appointmentId: com.google.firebase.dataconnect.OptionalVariable<AppointmentKey?> =
                com.google.firebase.dataconnect.OptionalVariable.Undefined
            var statusId: com.google.firebase.dataconnect.OptionalVariable<StatusTypeKey?> =
                com.google.firebase.dataconnect.OptionalVariable.Undefined
            

          return object : Builder {
            override var donationDate: com.google.firebase.dataconnect.LocalDate
              get() = throw UnsupportedOperationException("getting builder values is not supported")
              set(value_) { donationDate = value_ }
              
            override var donorName: String
              get() = throw UnsupportedOperationException("getting builder values is not supported")
              set(value_) { donorName = value_ }
              
            override var estimatedValue: Double
              get() = throw UnsupportedOperationException("getting builder values is not supported")
              set(value_) { estimatedValue = value_ }
              
            override var donorContact: String?
              get() = throw UnsupportedOperationException("getting builder values is not supported")
              set(value_) { donorContact = com.google.firebase.dataconnect.OptionalVariable.Value(value_) }
              
            override var observations: String?
              get() = throw UnsupportedOperationException("getting builder values is not supported")
              set(value_) { observations = com.google.firebase.dataconnect.OptionalVariable.Value(value_) }
              
            override var appointmentId: AppointmentKey?
              get() = throw UnsupportedOperationException("getting builder values is not supported")
              set(value_) { appointmentId = com.google.firebase.dataconnect.OptionalVariable.Value(value_) }
              
            override var statusId: StatusTypeKey?
              get() = throw UnsupportedOperationException("getting builder values is not supported")
              set(value_) { statusId = com.google.firebase.dataconnect.OptionalVariable.Value(value_) }
              
            
          }.apply(block_)
          .let {
            Variables(
              donationDate=donationDate,donorName=donorName,estimatedValue=estimatedValue,donorContact=donorContact,observations=observations,appointmentId=appointmentId,statusId=statusId,
            )
          }
        }
      }
    
  }
  

  
    @kotlinx.serialization.Serializable
  public data class Data(
  
    val donation_insert: DonationKey
  ) {
    
    
  }
  

  public companion object {
    public val operationName: String = "CreateDonation"

    public val dataDeserializer: kotlinx.serialization.DeserializationStrategy<Data> =
      kotlinx.serialization.serializer()

    public val variablesSerializer: kotlinx.serialization.SerializationStrategy<Variables> =
      kotlinx.serialization.serializer()
  }
}

public fun CreateDonationMutation.ref(
  
    donationDate: com.google.firebase.dataconnect.LocalDate,donorName: String,estimatedValue: Double,
  
    block_: CreateDonationMutation.Variables.Builder.() -> Unit = {}
  
): com.google.firebase.dataconnect.MutationRef<
    CreateDonationMutation.Data,
    CreateDonationMutation.Variables
  > =
  ref(
    
      CreateDonationMutation.Variables.build(
        donationDate=donationDate,donorName=donorName,estimatedValue=estimatedValue,
  
    block_
      )
    
  )

public suspend fun CreateDonationMutation.execute(
  
    donationDate: com.google.firebase.dataconnect.LocalDate,donorName: String,estimatedValue: Double,
  
    block_: CreateDonationMutation.Variables.Builder.() -> Unit = {}
  
  ): com.google.firebase.dataconnect.MutationResult<
    CreateDonationMutation.Data,
    CreateDonationMutation.Variables
  > =
  ref(
    
      donationDate=donationDate,donorName=donorName,estimatedValue=estimatedValue,
  
    block_
    
  ).execute()


