FROM maven:3.6.3-ibmjava-8-alpine
WORKDIR /assignment
ENTRYPOINT ["sh"]
CMD ["parking_lot.sh", "input.txt"]