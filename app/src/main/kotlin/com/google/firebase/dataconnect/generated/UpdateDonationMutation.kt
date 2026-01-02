
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



public interface UpdateDonationMutation :
    com.google.firebase.dataconnect.generated.GeneratedMutation<
      SasConnectorConnector,
      UpdateDonationMutation.Data,
      UpdateDonationMutation.Variables
    >
{
  
    @kotlinx.serialization.Serializable
  public data class Variables(
  
    val id: @kotlinx.serialization.Serializable(with = com.google.firebase.dataconnect.serializers.UUIDSerializer::class) java.util.UUID,
    val donationDate: com.google.firebase.dataconnect.OptionalVariable<com.google.firebase.dataconnect.LocalDate?>,
    val donorName: com.google.firebase.dataconnect.OptionalVariable<String?>,
    val estimatedValue: com.google.firebase.dataconnect.OptionalVariable<Double?>,
    val donorContact: com.google.firebase.dataconnect.OptionalVariable<String?>,
    val observations: com.google.firebase.dataconnect.OptionalVariable<String?>,
    val appointmentId: com.google.firebase.dataconnect.OptionalVariable<AppointmentKey?>,
    val statusId: com.google.firebase.dataconnect.OptionalVariable<StatusTypeKey?>
  ) {
    
    
      
      @kotlin.DslMarker public annotation class BuilderDsl

      @BuilderDsl
      public interface Builder {
        public var id: java.util.UUID
        public var donationDate: com.google.firebase.dataconnect.LocalDate?
        public var donorName: String?
        public var estimatedValue: Double?
        public var donorContact: String?
        public var observations: String?
        public var appointmentId: AppointmentKey?
        public var statusId: StatusTypeKey?
        
      }

      public companion object {
        @Suppress("NAME_SHADOWING")
        public fun build(
          id: java.util.UUID,
          block_: Builder.() -> Unit
        ): Variables {
          var id= id
            var donationDate: com.google.firebase.dataconnect.OptionalVariable<com.google.firebase.dataconnect.LocalDate?> =
                com.google.firebase.dataconnect.OptionalVariable.Undefined
            var donorName: com.google.firebase.dataconnect.OptionalVariable<String?> =
                com.google.firebase.dataconnect.OptionalVariable.Undefined
            var estimatedValue: com.google.firebase.dataconnect.OptionalVariable<Double?> =
                com.google.firebase.dataconnect.OptionalVariable.Undefined
            var donorContact: com.google.firebase.dataconnect.OptionalVariable<String?> =
                com.google.firebase.dataconnect.OptionalVariable.Undefined
            var observations: com.google.firebase.dataconnect.OptionalVariable<String?> =
                com.google.firebase.dataconnect.OptionalVariable.Undefined
            var appointmentId: com.google.firebase.dataconnect.OptionalVariable<AppointmentKey?> =
                com.google.firebase.dataconnect.OptionalVariable.Undefined
            var statusId: com.google.firebase.dataconnect.OptionalVariable<StatusTypeKey?> =
                com.google.firebase.dataconnect.OptionalVariable.Undefined
            

          return object : Builder {
            override var id: java.util.UUID
              get() = throw UnsupportedOperationException("getting builder values is not supported")
              set(value_) { id = value_ }
              
            override var donationDate: com.google.firebase.dataconnect.LocalDate?
              get() = throw UnsupportedOperationException("getting builder values is not supported")
              set(value_) { donationDate = com.google.firebase.dataconnect.OptionalVariable.Value(value_) }
              
            override var donorName: String?
              get() = throw UnsupportedOperationException("getting builder values is not supported")
              set(value_) { donorName = com.google.firebase.dataconnect.OptionalVariable.Value(value_) }
              
            override var estimatedValue: Double?
              get() = throw UnsupportedOperationException("getting builder values is not supported")
              set(value_) { estimatedValue = com.google.firebase.dataconnect.OptionalVariable.Value(value_) }
              
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
              id=id,donationDate=donationDate,donorName=donorName,estimatedValue=estimatedValue,donorContact=donorContact,observations=observations,appointmentId=appointmentId,statusId=statusId,
            )
          }
        }
      }
    
  }
  

  
    @kotlinx.serialization.Serializable
  public data class Data(
  
    val donation_update: DonationKey?
  ) {
    
    
  }
  

  public companion object {
    public val operationName: String = "UpdateDonation"

    public val dataDeserializer: kotlinx.serialization.DeserializationStrategy<Data> =
      kotlinx.serialization.serializer()

    public val variablesSerializer: kotlinx.serialization.SerializationStrategy<Variables> =
      kotlinx.serialization.serializer()
  }
}

public fun UpdateDonationMutation.ref(
  
    id: java.util.UUID,
  
    block_: UpdateDonationMutation.Variables.Builder.() -> Unit = {}
  
): com.google.firebase.dataconnect.MutationRef<
    UpdateDonationMutation.Data,
    UpdateDonationMutation.Variables
  > =
  ref(
    
      UpdateDonationMutation.Variables.build(
        id=id,
  
    block_
      )
    
  )

public suspend fun UpdateDonationMutation.execute(
  
    id: java.util.UUID,
  
    block_: UpdateDonationMutation.Variables.Builder.() -> Unit = {}
  
  ): com.google.firebase.dataconnect.MutationResult<
    UpdateDonationMutation.Data,
    UpdateDonationMutation.Variables
  > =
  ref(
    
      id=id,
  
    block_
    
  ).execute()


