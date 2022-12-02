package com.basic.myspringboot.entity;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

@Entity
@Table(name="users")
@Getter @Setter
public class User {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JacksonXmlProperty(isAttribute = true)
    private Long id;

    @Column(nullable = false)
    @JacksonXmlProperty
    @NotEmpty(message = "Name(이름)은 필수 입력 항목입니다.")
    private String name;

    @Column(unique = true, nullable = false)
    @JacksonXmlProperty
    @NotBlank(message = "Email(이메일)은 필수 입력 항목입니다.")
    @Email(message = "올바른 Email 형식이 아닙니다.")
    private String email;
}
