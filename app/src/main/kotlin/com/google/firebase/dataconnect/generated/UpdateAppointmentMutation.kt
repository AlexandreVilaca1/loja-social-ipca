
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



public interface UpdateAppointmentMutation :
    com.google.firebase.dataconnect.generated.GeneratedMutation<
      SasConnectorConnector,
      UpdateAppointmentMutation.Data,
      UpdateAppointmentMutation.Variables
    >
{
  
    @kotlinx.serialization.Serializable
  public data class Variables(
  
    val id: @kotlinx.serialization.Serializable(with = com.google.firebase.dataconnect.serializers.UUIDSerializer::class) java.util.UUID,
    val appointmentDate: com.google.firebase.dataconnect.OptionalVariable<com.google.firebase.dataconnect.LocalDate?>,
    val appointmentTime: com.google.firebase.dataconnect.OptionalVariable<String?>,
    val type: com.google.firebase.dataconnect.OptionalVariable<String?>,
    val observations: com.google.firebase.dataconnect.OptionalVariable<String?>,
    val userId: com.google.firebase.dataconnect.OptionalVariable<UserKey?>,
    val beneficiaryId: com.google.firebase.dataconnect.OptionalVariable<BeneficiaryKey?>,
    val statusId: com.google.firebase.dataconnect.OptionalVariable<StatusTypeKey?>
  ) {
    
    
      
      @kotlin.DslMarker public annotation class BuilderDsl

      @BuilderDsl
      public interface Builder {
        public var id: java.util.UUID
        public var appointmentDate: com.google.firebase.dataconnect.LocalDate?
        public var appointmentTime: String?
        public var type: String?
        public var observations: String?
        public var userId: UserKey?
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
            var appointmentDate: com.google.firebase.dataconnect.OptionalVariable<com.google.firebase.dataconnect.LocalDate?> =
                com.google.firebase.dataconnect.OptionalVariable.Undefined
            var appointmentTime: com.google.firebase.dataconnect.OptionalVariable<String?> =
                com.google.firebase.dataconnect.OptionalVariable.Undefined
            var type: com.google.firebase.dataconnect.OptionalVariable<String?> =
                com.google.firebase.dataconnect.OptionalVariable.Undefined
            var observations: com.google.firebase.dataconnect.OptionalVariable<String?> =
                com.google.firebase.dataconnect.OptionalVariable.Undefined
            var userId: com.google.firebase.dataconnect.OptionalVariable<UserKey?> =
                com.google.firebase.dataconnect.OptionalVariable.Undefined
            var beneficiaryId: com.google.firebase.dataconnect.OptionalVariable<BeneficiaryKey?> =
                com.google.firebase.dataconnect.OptionalVariable.Undefined
            var statusId: com.google.firebase.dataconnect.OptionalVariable<StatusTypeKey?> =
                com.google.firebase.dataconnect.OptionalVariable.Undefined
            

          return object : Builder {
            override var id: java.util.UUID
              get() = throw UnsupportedOperationException("getting builder values is not supported")
              set(value_) { id = value_ }
              
            override var appointmentDate: com.google.firebase.dataconnect.LocalDate?
              get() = throw UnsupportedOperationException("getting builder values is not supported")
              set(value_) { appointmentDate = com.google.firebase.dataconnect.OptionalVariable.Value(value_) }
              
            override var appointmentTime: String?
              get() = throw UnsupportedOperationException("getting builder values is not supported")
              set(value_) { appointmentTime = com.google.firebase.dataconnect.OptionalVariable.Value(value_) }
              
            override var type: String?
              get() = throw UnsupportedOperationException("getting builder values is not supported")
              set(value_) { type = com.google.firebase.dataconnect.OptionalVariable.Value(value_) }
              
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
              id=id,appointmentDate=appointmentDate,appointmentTime=appointmentTime,type=type,observations=observations,userId=userId,beneficiaryId=beneficiaryId,statusId=statusId,
            )
          }
        }
      }
    
  }
  

  
    @kotlinx.serialization.Serializable
  public data class Data(
  
    val appointment_update: AppointmentKey?
  ) {
    
    
  }
  

  public companion object {
    public val operationName: String = "UpdateAppointment"

    public val dataDeserializer: kotlinx.serialization.DeserializationStrategy<Data> =
      kotlinx.serialization.serializer()

    public val variablesSerializer: kotlinx.serialization.SerializationStrategy<Variables> =
      kotlinx.serialization.serializer()
  }
}

public fun UpdateAppointmentMutation.ref(
  
    id: java.util.UUID,
  
    block_: UpdateAppointmentMutation.Variables.Builder.() -> Unit = {}
  
): com.google.firebase.dataconnect.MutationRef<
    UpdateAppointmentMutation.Data,
    UpdateAppointmentMutation.Variables
  > =
  ref(
    
      UpdateAppointmentMutation.Variables.build(
        id=id,
  
    block_
      )
    
  )

public suspend fun UpdateAppointmentMutation.execute(
  
    id: java.util.UUID,
  
    block_: UpdateAppointmentMutation.Variables.Builder.() -> Unit = {}
  
  ): com.google.firebase.dataconnect.MutationResult<
    UpdateAppointmentMutation.Data,
    UpdateAppointmentMutation.Variables
  > =
  ref(
    
      id=id,
  
    block_
    
  ).execute()


