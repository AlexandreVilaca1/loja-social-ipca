
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



public interface UpdateAppointmentStatusMutation :
    com.google.firebase.dataconnect.generated.GeneratedMutation<
      SasConnectorConnector,
      UpdateAppointmentStatusMutation.Data,
      UpdateAppointmentStatusMutation.Variables
    >
{
  
    @kotlinx.serialization.Serializable
  public data class Variables(
  
    val id: @kotlinx.serialization.Serializable(with = com.google.firebase.dataconnect.serializers.UUIDSerializer::class) java.util.UUID,
    val statusId: StatusTypeKey
  ) {
    
    
  }
  

  
    @kotlinx.serialization.Serializable
  public data class Data(
  
    val appointment_update: AppointmentKey?
  ) {
    
    
  }
  

  public companion object {
    public val operationName: String = "UpdateAppointmentStatus"

    public val dataDeserializer: kotlinx.serialization.DeserializationStrategy<Data> =
      kotlinx.serialization.serializer()

    public val variablesSerializer: kotlinx.serialization.SerializationStrategy<Variables> =
      kotlinx.serialization.serializer()
  }
}

public fun UpdateAppointmentStatusMutation.ref(
  
    id: java.util.UUID,statusId: StatusTypeKey,
  
  
): com.google.firebase.dataconnect.MutationRef<
    UpdateAppointmentStatusMutation.Data,
    UpdateAppointmentStatusMutation.Variables
  > =
  ref(
    
      UpdateAppointmentStatusMutation.Variables(
        id=id,statusId=statusId,
  
      )
    
  )

public suspend fun UpdateAppointmentStatusMutation.execute(
  
    id: java.util.UUID,statusId: StatusTypeKey,
  
  
  ): com.google.firebase.dataconnect.MutationResult<
    UpdateAppointmentStatusMutation.Data,
    UpdateAppointmentStatusMutation.Variables
  > =
  ref(
    
      id=id,statusId=statusId,
  
    
  ).execute()


