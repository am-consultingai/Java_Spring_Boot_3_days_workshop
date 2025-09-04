package org.kahoot.builder.dto;

public class CreateQuizRequest {
        private int ownerId;
        private String title;
        private String description;

        public CreateQuizRequest() {}

        public String getTitle() {
                return title;
        }
        public void setTitle(String title) {
                this.title = title;
        }

        public String getDescription() {
                return description;
        }

        public void setDescription(String description) {
                this.description = description;
        }

        public int getOwnerId() {
                return ownerId;
        }

        public void setOwnerId(int ownerId) {
                this.ownerId = ownerId;
        }
}
