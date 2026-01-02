
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



public interface CreateAppointmentMutation :
    com.google.firebase.dataconnect.generated.GeneratedMutation<
      SasConnectorConnector,
      CreateAppointmentMutation.Data,
      CreateAppointmentMutation.Variables
    >
{
  
    @kotlinx.serialization.Serializable
  public data class Variables(
  
    val appointmentDate: com.google.firebase.dataconnect.LocalDate,
    val appointmentTime: String,
    val type: String,
    val observations: com.google.firebase.dataconnect.OptionalVariable<String?>,
    val userId: com.google.firebase.dataconnect.OptionalVariable<UserKey?>,
    val beneficiaryId: com.google.firebase.dataconnect.OptionalVariable<BeneficiaryKey?>,
    val statusId: com.google.firebase.dataconnect.OptionalVariable<StatusTypeKey?>
  ) {
    
    
      
      @kotlin.DslMarker public annotation class BuilderDsl

      @BuilderDsl
      public interface Builder {
        public var appointmentDate: com.google.firebase.dataconnect.LocalDate
        public var appointmentTime: String
        public var type: String
        public var observations: String?
        public var userId: UserKey?
        public var beneficiaryId: BeneficiaryKey?
        public var statusId: StatusTypeKey?
        
      }

      public companion object {
        @Suppress("NAME_SHADOWING")
        public fun build(
          appointmentDate: com.google.firebase.dataconnect.LocalDate,appointmentTime: String,type: String,
          block_: Builder.() -> Unit
        ): Variables {
          var appointmentDate= appointmentDate
            var appointmentTime= appointmentTime
            var type= type
            var observations: com.google.firebase.dataconnect.OptionalVariable<String?> =
                com.google.firebase.dataconnect.OptionalVariable.Undefined
            var userId: com.google.firebase.dataconnect.OptionalVariable<UserKey?> =
                com.google.firebase.dataconnect.OptionalVariable.Undefined
            var beneficiaryId: com.google.firebase.dataconnect.OptionalVariable<BeneficiaryKey?> =
                com.google.firebase.dataconnect.OptionalVariable.Undefined
            var statusId: com.google.firebase.dataconnect.OptionalVariable<StatusTypeKey?> =
                com.google.firebase.dataconnect.OptionalVariable.Undefined
            

          return object : Builder {
            override var appointmentDate: com.google.firebase.dataconnect.LocalDate
              get() = throw UnsupportedOperationException("getting builder values is not supported")
              set(value_) { appointmentDate = value_ }
              
            override var appointmentTime: String
              get() = throw UnsupportedOperationException("getting builder values is not supported")
              set(value_) { appointmentTime = value_ }
              
            override var type: String
              get() = throw UnsupportedOperationException("getting builder values is not supported")
              set(value_) { type = value_ }
              
            override var observations: String?
              get() = throw UnsupportedOperationException("getting builder values is not supported")
              set(value_) { observations = com.google.firebase.dataconnect.OptionalVariable.Value(value_) }
              
            override var userId: UserKey?
              get() = throw UnsupportedOperationException("getting builder values is not supported")
              set(value_) { userId = com.google.firebase.dataconnect.OptionalVariable.Value(value_) }
              
            override var beneficiaryId: BeneficiaryKey?
              get() = throw UnsupportedOperationException("getting builder values is not supported")
              set(value_) { beneficiaryId = com.google.firebase.dataconnect.OptionalVariable.Value(value_) }
              
            override var statusId: StatusTypeKey?
              get() = throw UnsupportedOperationException("getting builder values is not supported")
              set(value_) { statusId = com.google.firebase.dataconnect.OptionalVariable.Value(value_) }
              
            
          }.apply(block_)
          .let {
            Variables(
              appointmentDate=appointmentDate,appointmentTime=appointmentTime,type=type,observations=observations,userId=userId,beneficiaryId=beneficiaryId,statusId=statusId,
            )
          }
        }
      }
    
  }
  

  
    @kotlinx.serialization.Serializable
  public data class Data(
  
    val appointment_insert: AppointmentKey
  ) {
    
    
  }
  

  public companion object {
    public val operationName: String = "CreateAppointment"

    public val dataDeserializer: kotlinx.serialization.DeserializationStrategy<Data> =
      kotlinx.serialization.serializer()

    public val variablesSerializer: kotlinx.serialization.SerializationStrategy<Variables> =
      kotlinx.serialization.serializer()
  }
}

public fun CreateAppointmentMutation.ref(
  
    appointmentDate: com.google.firebase.dataconnect.LocalDate,appointmentTime: String,type: String,
  
    block_: CreateAppointmentMutation.Variables.Builder.() -> Unit = {}
  
): com.google.firebase.dataconnect.MutationRef<
    CreateAppointmentMutation.Data,
    CreateAppointmentMutation.Variables
  > =
  ref(
    
      CreateAppointmentMutation.Variables.build(
        appointmentDate=appointmentDate,appointmentTime=appointmentTime,type=type,
  
    block_
      )
    
  )

public suspend fun CreateAppointmentMutation.execute(
  
    appointmentDate: com.google.firebase.dataconnect.LocalDate,appointmentTime: String,type: String,
  
    block_: CreateAppointmentMutation.Variables.Builder.() -> Unit = {}
  
  ): com.google.firebase.dataconnect.MutationResult<
    CreateAppointmentMutation.Data,
    CreateAppointmentMutation.Variables
  > =
  ref(
    
      appointmentDate=appointmentDate,appointmentTime=appointmentTime,type=type,
  
    block_
    
  ).execute()


