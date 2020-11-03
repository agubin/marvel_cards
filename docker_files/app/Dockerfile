FROM agubin/jdk8

WORKDIR /home/
RUN git clone https://github.com/agubin/marvel_cards.git
COPY conf_app_prop.sh /home/marvel_cards/cards/src/main/resources/
WORKDIR /home/marvel_cards/cards/src/main/resources/
RUN bash conf_app_prop.sh
RUN rm conf_app_prop.sh
WORKDIR /home/
RUN mkdir fileStorage
WORKDIR /home/marvel_cards/cards/

ENTRYPOINT ./mvnw spring-boot:run
