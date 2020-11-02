import face_recognition
import cv2
from select import select
import sys
import time
import os
import numpy as np

video_capture = cv2.VideoCapture(0)

file_list = os.listdir("C:\\Project\\images")
imageNames = [i for i in file_list if i.find('.png') != -1 or i.find('jpg') != -1]

names = []
for image in imageNames:
    names.append(image.split('.')[0])

image_s = []
for imageName in imageNames:
    image_s.append(face_recognition.load_image_file("C:\\Project\\images\\%s" % imageName))

face_encoding_s = []
for image in image_s:
    face_encoding_s.append(face_recognition.face_encodings(image)[0])

#print(face_encoding_s)
#print("type:{}".format(type(face_encoding_s)))

#인코딩값 = [][128] 리스트 사이즈
#for(i in face_encoding_s[0])

known_face_encodings = face_encoding_s

known_face_names = names
#print(len(known_face_names))

# Initialize some variables
face_locations = []
face_encodings = []
face_names = []

IMG_PATH="C:\\Project\\images\\log\\log.jpg"
counter = 0
#color
green = (76, 177, 32)
red = (0, 0, 255)
key_name =""

while True:
    ret, frame = video_capture.read()
    rgb_frame = frame[:, :, ::-1]
    color = red
    # Find all the faces and face encodings in the current frame of video
    face_locations = face_recognition.face_locations(rgb_frame)
    face_encodings = face_recognition.face_encodings(rgb_frame, face_locations)
        
    face_names = []
    for (top, right, bottom, left), face_encoding in zip(face_locations, face_encodings):

        # See if the face is a match for the known face(s)
        matches = face_recognition.compare_faces(known_face_encodings, face_encoding)
        name = "Unknown"

        # If a match was found in known_face_encodings, just use the first one.
        if True in matches:
            first_match_index = matches.index(True)
            #name = known_face_names[first_match_index]
            name = known_face_names[first_match_index]
            counter = counter +1
            color = green
            if counter==5:
                key_name = name



        # Draw a box around the face
        #cv2.rectangle(frame, (left, top), (right, bottom), (0, 0, 255), 2)
        cv2.rectangle(frame, (left, top), (right, bottom),  color, 2)

        # Draw a label with a name below the face
        #cv2.rectangle(frame, (left, bottom - 35), (right, bottom), (0, 0, 255), cv2.FILLED)
        cv2.rectangle(frame, (left, bottom - 35), (right, bottom), color, cv2.FILLED)
        font = cv2.FONT_HERSHEY_DUPLEX
        #cv2.putText(frame, name, (left + 6, bottom - 6), font, 1.0, (255, 255, 255), 1)
        cv2.putText(frame, "Checking...", (left + 6, bottom - 6), font, 1.0, (255, 255, 255), 1)

        #cv2.imwrite(IMG_PATH, frame)


    # Display the resulting image
    #cv2.imwrite("C:\\Project\\log\\log.jpg", frame)
    cv2.imshow('Video', frame)

    # Hit 'q' on the keyboard to quit!
    if cv2.waitKey(1) & 0xFF == ord('q') :
        break
    elif counter==5:
        break
    
#end while

# Release handle to the webcam
video_capture.release()
cv2.destroyAllWindows()



print("암호화 시작")
print("key_file : " + key_name+".jpg")


exit(0)