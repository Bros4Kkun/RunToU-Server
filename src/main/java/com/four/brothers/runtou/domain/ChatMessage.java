package com.four.brothers.runtou.domain;

import com.four.brothers.runtou.domain.base.BaseTimeEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class ChatMessage extends BaseTimeEntity {
  @Id @GeneratedValue(strategy = GenerationType.AUTO)
  @Column
  private Long id;

  @ManyToOne(fetch = FetchType.EAGER, optional = false)
  @JoinColumn(name = "USER_ID", nullable = false)
  private User user;

  @ManyToOne(fetch = FetchType.EAGER, optional = false)
  @JoinColumn(name = "CHAT_ROOM_ID", nullable = false)
  private ChatRoom chatRoom;

  @Lob
  @Column(length = 2024, nullable = false)
  private String content; //CLOB 타입

  public ChatMessage(User user, ChatRoom chatRoom, String content) {
    this.user = user;
    this.chatRoom = chatRoom;
    this.content = content;
  }

  protected void setUser(User user) {
    this.user = user;
  }

  protected void setChatRoom(ChatRoom chatRoom) {
    this.chatRoom = chatRoom;
  }
}
