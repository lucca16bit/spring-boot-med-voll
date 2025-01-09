create table consultas(

    id bigint not null generated by default as identity,
    medico_id bigint not null,
    paciente_id bigint not null,
    data timestamp not null,

    primary key(id),
    foreign key(medico_id) references medicos(id),
    foreign key(paciente_id) references pacientes(id)
);